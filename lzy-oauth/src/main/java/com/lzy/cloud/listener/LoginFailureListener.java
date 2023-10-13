package com.lzy.cloud.listener;

import com.lzy.cloud.service.LzyCloudUserDetails;
import com.lzy.platform.base.constant.AuthConstant;
import com.lzy.platform.base.constant.LzyCloudConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录失败监听器
 * 当登录失败时的调用，当密码错误过多时，则锁定账户
 *
 * @author 2428
 * @date 2022/08/04
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserDetailsService userDetailsService;

    private final RedisTemplate redisTemplate;

    /**
     * 超过尝试次数，锁定账户
     */
    @Value("${system.maxTryTimes}")
    private int maxTryTimes;

    /**
     * 锁定时间，单位 分钟
     */
    @Value("${system.maxLockTime}")
    private long maxLockTime;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {

        if (event.getException() instanceof UsernameNotFoundException
                || event.getException().getCause() instanceof InvalidTokenException) {
            return;
        }

        try {
            String userName = event.getAuthentication().getName();

            LzyCloudUserDetails user = (LzyCloudUserDetails) userDetailsService.loadUserByUsername(userName);

            if (null != user) {
                Object lockTimes = redisTemplate.boundValueOps(AuthConstant.LOCK_ACCOUNT_PREFIX + user.getId()).get();
                if(null == lockTimes || (int)lockTimes <= maxTryTimes){
                    redisTemplate.boundValueOps(AuthConstant.LOCK_ACCOUNT_PREFIX + user.getId()).increment(LzyCloudConstant.Number.ONE);
                    redisTemplate.expire(AuthConstant.LOCK_ACCOUNT_PREFIX + user.getId(), maxLockTime , TimeUnit.MINUTES);
                }
            }
        } catch (Exception e)
        {
            log.error("增加账户锁定次数失败：{}", e);
        }
    }
}
