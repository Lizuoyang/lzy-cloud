package com.lzy.cloud.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.lzy.cloud.enums.AuthEnum;
import com.lzy.cloud.exception.LzyCloudOAuth2Exception;
import com.lzy.cloud.feign.IUserFeign;
import com.lzy.platform.base.constant.AuthConstant;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.base.domain.LzyCloudUser;
import com.lzy.platform.base.enums.ResultCodeEnum;
import com.lzy.platform.base.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName LzyCloudUserDetailsServiceImpl
 * @Description 用于spring security 获取用户信息接口
 * @Author LiZuoYang
 * @Date 2022/6/21 11:11
 **/
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LzyCloudUserDetailsServiceImpl implements UserDetailsService {
    private final IUserFeign userFeign;
    private final HttpServletRequest request;
    private final RedisTemplate redisTemplate;

    /**
     * 密码最大尝试次数
     */
    @Value("${system.maxTryTimes}")
    private int maxTryTimes;

    /**
     * 不需要验证码登录的最大尝试次数
     */
    @Value("${system.maxNonCaptchaTimes}")
    private int maxNonCaptchaTimes;
    @Override
    public LzyCloudUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 获取登录类型：密码，二维码，验证码
        String authGrantType = request.getParameter(AuthConstant.GRANT_TYPE);
        // 获取客户端id
        String clientId = request.getParameter(AuthConstant.AUTH_CLIENT_ID);

        // 远程调用返回结果
        Result<Object> result;

        result = userFeign.queryUserByAccount(username);


        // 判断返回信息
        if (ObjectUtil.isNotNull(result) && result.isSuccess()) {
            LzyCloudUser lzyCloudUser = new LzyCloudUser();
            BeanUtil.copyProperties(result.getData(), lzyCloudUser, false);

            // 用户名或者密码错误
            if (ObjectUtil.isNull(lzyCloudUser) || ObjectUtil.isNull(lzyCloudUser.getId())) {
                log.info(ResultCodeEnum.INVALID_USERNAME.msg);
                throw new UsernameNotFoundException(ResultCodeEnum.INVALID_USERNAME.msg);
            }

            // 用户没有配置角色
            if (CollectionUtil.isEmpty(lzyCloudUser.getRoleIdList())) {
                log.info(ResultCodeEnum.INVALID_ROLE.msg);
                throw new UserDeniedAuthorizationException(ResultCodeEnum.INVALID_ROLE.msg);
            }

            // 获取redis中账号密码输入错误数
            Object lockTimes = redisTemplate.boundValueOps(AuthConstant.LOCK_ACCOUNT_PREFIX + lzyCloudUser.getId()).get();
            /** 判断账号密码输入错误几次，如果输入错误多次，则锁定账号
             输入错误大于配置的次数，必须选择captcha */
            if (ObjectUtil.isNotNull(lockTimes) && (int) lockTimes > maxNonCaptchaTimes
                    && (StrUtil.isEmpty(authGrantType) || (StrUtil.isNotEmpty(authGrantType))) && !AuthEnum.CAPTCHA.code.equals(authGrantType)
            ) {
                log.info(ResultCodeEnum.INVALID_PASSWORD_CAPTCHA.msg);
                throw new LzyCloudOAuth2Exception(ResultCodeEnum.INVALID_PASSWORD_CAPTCHA.msg);
            }

            // 判断账号密码是否被锁定（账户过期，凭证过期等可在此处扩展）
            if (ObjectUtil.isNotNull(lockTimes) && (int) lockTimes > maxTryTimes) {
                log.info(ResultCodeEnum.PASSWORD_TRY_MAX_ERROR.msg);
                throw new LockedException(ResultCodeEnum.PASSWORD_TRY_MAX_ERROR.msg);
            }

            // 判断账号是否被禁用
            String userStatus = lzyCloudUser.getStatus();
            if (String.valueOf(LzyCloudConstant.DISABLE).equals(userStatus)) {
                log.info(ResultCodeEnum.DISABLED_ACCOUNT.msg);
                throw new DisabledException(ResultCodeEnum.DISABLED_ACCOUNT.msg);
            }

            /**
             * enabled 账户是否被禁用  !String.valueOf(LzyCloudConstant.DISABLE).equals(lzyCloudUser.getStatus())
             * AccountNonExpired 账户是否过期  此框架暂时不提供账户过期功能，可根据业务需求在此处扩展
             * AccountNonLocked  账户是否被锁  密码尝试次数过多，则锁定账户
             * CredentialsNonExpired 凭证是否过期
             */
            //TODO 后面引入MapStruct简化对象转换
            return new LzyCloudUserDetails(lzyCloudUser.getId(), lzyCloudUser.getOauthId(),
                    lzyCloudUser.getNickname(), lzyCloudUser.getRealName(), lzyCloudUser.getOrganizationId(),
                    lzyCloudUser.getOrganizationName(),
                    lzyCloudUser.getOrganizationIds(), lzyCloudUser.getOrganizationNames(), lzyCloudUser.getRoleId(), lzyCloudUser.getRoleIds(), lzyCloudUser.getRoleName(), lzyCloudUser.getRoleNames(),
                    lzyCloudUser.getRoleIdList(), lzyCloudUser.getRoleKeyList(), lzyCloudUser.getResourceKeyList(),
                    lzyCloudUser.getDataPermissionTypeList(), lzyCloudUser.getOrganizationIdList(),
                    lzyCloudUser.getAvatar(), lzyCloudUser.getAccount(), lzyCloudUser.getPassword(), !String.valueOf(LzyCloudConstant.DISABLE).equals(lzyCloudUser.getStatus()), true, true, true,
                    this.getPrivileges(lzyCloudUser.getRoleKeyList(), lzyCloudUser.getResourceUrlList()));

        } else {
            log.info(result.getMsg());
            throw new UsernameNotFoundException(result.getMsg());
        }
    }

    /**
     * 设置SpringSecurity需要的role和resource
     * @param roles
     * @param resources
     * @return
     */
    private final List<GrantedAuthority> getPrivileges(final Collection<String> roles, final Collection<String> resources) {
        final List<GrantedAuthority> authorities = new ArrayList<>();

        for (final String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        //不将resource权限加入token，这样会导致请求头很大
//        for (final String resource : resources) {
//            authorities.add(new SimpleGrantedAuthority(resource));
//        }

        return authorities;
    }
}
