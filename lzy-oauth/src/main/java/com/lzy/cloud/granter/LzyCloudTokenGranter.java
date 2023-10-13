package com.lzy.cloud.granter;

import com.anji.captcha.service.CaptchaService;
import com.lzy.cloud.feign.IUserFeign;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义token
 *
 * @author 2428
 * @date 2022/06/22
 */
public class LzyCloudTokenGranter {

    /**
     * 自定义tokenGranter
     */
    public static TokenGranter getTokenGranter(final AuthenticationManager authenticationManager,
                                               final AuthorizationServerEndpointsConfigurer endpoints, RedisTemplate redisTemplate, IUserFeign userFeign,
                                               CaptchaService captchaService, UserDetailsService userDetailsService,
                                               String captchaType, String secretKey, String secretKeySalt) {
        // 默认tokenGranter集合
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        // 增加验证码模式
        granters.add(new CaptchaTokenGranter(authenticationManager, endpoints.getTokenServices(),
            endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), redisTemplate, captchaService,
            captchaType));
        // 组合tokenGranter集合
        return new CompositeTokenGranter(granters);
    }

}
