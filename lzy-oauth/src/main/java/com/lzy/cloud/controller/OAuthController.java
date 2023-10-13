package com.lzy.cloud.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.lzy.cloud.feign.IUserFeign;
import com.lzy.cloud.utils.JwtUtils;
import com.lzy.platform.base.annotation.auth.CurrentUser;
import com.lzy.platform.base.constant.AuthConstant;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.base.constant.TokenConstant;
import com.lzy.platform.base.domain.LzyCloudUser;
import com.lzy.platform.base.result.Result;
import com.lzy.platform.constant.CaptchaConstant;
import com.lzy.platform.domain.ImageCaptcha;
import com.lzy.platform.domain.Oauth2Token;
import com.lzy.platform.springboot.utils.LzyCloudAuthUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OAuthController
 * @Description OAuth2认证中心
 * @Author LiZuoYang
 * @Date 2022/6/22 16:26
 **/
@Api(tags = "OAuth2认证中心")
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/oauth")
public class OAuthController {
    private final KeyPair keyPair;
    private final RedisTemplate redisTemplate;
    private final IUserFeign userFeign;
    private final TokenEndpoint tokenEndpoint;
    private final CaptchaService captchaService;

    @Value("${captcha.type}")
    private String captchaType;

    @ApiOperation("获取系统配置的验证码类型")
    @GetMapping("/captcha/type")
    public Result captchaType() {
        return Result.data(captchaType);
    }

    @ApiOperation("生成滑动验证码")
    @PostMapping("/captcha")
    public Result captcha(@RequestBody CaptchaVO captchaVO) {
        ResponseModel responseModel = captchaService.get(captchaVO);
        return Result.data(responseModel);
    }

    @ApiOperation("滑动验证码验证")
    @PostMapping("/captcha/check")
    public Result captchaCheck(@RequestBody CaptchaVO captchaVO) {
        ResponseModel responseModel = captchaService.check(captchaVO);
        return Result.data(responseModel);
    }

    @ApiOperation("生成图片验证码")
    @RequestMapping("/captcha/image")
    public Result captchaImage() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String captchaCode = specCaptcha.text().toLowerCase();
        String captchaKey = RandomUtil.randomString(10);
        // 存入redis并设置过期时间为5分钟
        redisTemplate.opsForValue().set(CaptchaConstant.IMAGE_CAPTCHA_KEY + captchaKey, captchaCode, LzyCloudConstant.Number.FIVE, TimeUnit.MINUTES);
        ImageCaptcha imageCaptcha = new ImageCaptcha();
        imageCaptcha.setCaptchaKey(captchaKey);
        imageCaptcha.setCaptchaImage(specCaptcha.toBase64());
        // 将key和base64返回给前端
        return Result.data(imageCaptcha);
    }

    /**
     * 获取RSA公钥
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/public_key")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

    /**
     * 退出登录需要需要登录的一点思考：
     * 1、如果不需要登录，那么在调用接口的时候就需要把token传过来，且系统不校验token有效性，此时如果系统被攻击，不停的大量发送token，最后会把redis充爆
     * 2、如果调用退出接口必须登录，那么系统会调用token校验有效性，refresh_token通过参数传过来加入黑名单
     * 综上：选择调用退出接口需要登录的方式
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {

        String token = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        String refreshToken = request.getParameter(AuthConstant.REFRESH_TOKEN);
        long currentTimeSeconds = System.currentTimeMillis() / LzyCloudConstant.Number.THOUSAND;

        // 清除账户锁定次数
        LzyCloudUser currentUser = LzyCloudAuthUtils.getCurrentUser();
        Object lockTimes = redisTemplate.boundValueOps(AuthConstant.LOCK_ACCOUNT_PREFIX + currentUser.getId()).get();
        if (ObjectUtil.isNotNull(lockTimes)) {
            redisTemplate.delete(AuthConstant.LOCK_ACCOUNT_PREFIX + currentUser.getId());
        }

        // 将token和refresh_token同时加入黑名单
        String[] tokenArray = new String[LzyCloudConstant.Number.TWO];
        tokenArray[LzyCloudConstant.Number.ZERO] = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        tokenArray[LzyCloudConstant.Number.ONE] = refreshToken.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        for (int i = LzyCloudConstant.Number.ZERO; i < tokenArray.length; i++) {
            String realToken = tokenArray[i];
            if (realToken != null && !ObjectUtil.equals(realToken, "null")) {
                JSONObject jsonObject = JwtUtils.decodeJwt(realToken);
                String jti = jsonObject.getAsString(TokenConstant.JTI);
                Long exp = Long.parseLong(jsonObject.getAsString(TokenConstant.EXP));
                if (exp - currentTimeSeconds > LzyCloudConstant.Number.ZERO) {
                    redisTemplate.opsForValue().set(AuthConstant.TOKEN_BLACKLIST + jti, jti, (exp - currentTimeSeconds), TimeUnit.SECONDS);
                }
            }
        }
        return Result.success();
    }

    @ApiOperation("OAuth2生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码"),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码"),
            @ApiImplicitParam(name = "encryptedData", value = "包括敏感数据在内的完整用户信息的加密数据"),
            @ApiImplicitParam(name = "iv", value = "加密算法的初始向量"),
    })
    @PostMapping("/token")
    public Result postAccessToken(@ApiIgnore Principal principal, @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        log.info("params: {}", JSONObject.toJSONString(parameters));
        //先对密码进行处理，取account和md5加密密码
        String username = parameters.get("username");
        String password = parameters.get("password");
        Result<Object> result = userFeign.queryUserByAccount(username);
        if (null != result && result.isSuccess()) {
            LzyCloudUser lzyCloudUser = new LzyCloudUser();
            BeanUtil.copyProperties(result.getData(), lzyCloudUser, false);
            if (!StringUtils.isEmpty(lzyCloudUser.getAccount())) {
                username = lzyCloudUser.getAccount();
                password = AuthConstant.BCRYPT + lzyCloudUser.getAccount() + password;
                parameters.put("username", username);
                parameters.put("password", password);
            }
        }

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        DefaultExpiringOAuth2RefreshToken refreshToken = (DefaultExpiringOAuth2RefreshToken)oAuth2AccessToken.getRefreshToken();
        Oauth2Token oauth2Token = Oauth2Token.builder()
                .token(oAuth2AccessToken.getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .exp(oAuth2AccessToken.getExpiration().getTime() / LzyCloudConstant.Number.THOUSAND)
                .refreshToken(refreshToken.getValue())
                .refreshExpiresIn((int) (refreshToken.getExpiration().getTime() / LzyCloudConstant.Number.THOUSAND - System.currentTimeMillis() / LzyCloudConstant.Number.THOUSAND))
                .refreshExp(refreshToken.getExpiration().getTime() / LzyCloudConstant.Number.THOUSAND)
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX)
                .build();
        return Result.data(oauth2Token);
    }

    /**
     * 获取当前用户信息
     *
     * @param user 用户
     * @return {@link Result}<{@link LzyCloudUser}>
     */
    @GetMapping("/user/info")
    public Result<LzyCloudUser> currentUser(@ApiIgnore @CurrentUser LzyCloudUser user) {
        return Result.data(user);
    }
}
