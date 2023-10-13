package com.lzy.cloud.config;

import com.anji.captcha.service.CaptchaService;
import com.lzy.cloud.exception.LzyCloudOAuth2ExceptionTranslator;
import com.lzy.cloud.feign.IUserFeign;
import com.lzy.cloud.granter.LzyCloudTokenGranter;
import com.lzy.cloud.service.LzyCloudClientDetailsServiceImpl;
import com.lzy.cloud.service.LzyCloudUserDetails;
import com.lzy.cloud.token.LzyCloudTokenServices;
import com.lzy.platform.base.constant.AuthConstant;
import com.lzy.platform.base.constant.TokenConstant;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.*;

/**
 * @ClassName AuthorizationServerConfig
 * @Description 认证服务配置，用于认证服务相关配置，正式环境请一定记得修改lzycloud.jks配置的密码，这里默认为123456。TokenEnhancer 为登录用户的扩展信息
 * @Author LiZuoYang
 * @Date 2022/6/22 10:38
 **/
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final DataSource dataSource;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final IUserFeign userFeign;
    private final RedisTemplate redisTemplate;
    private final CaptchaService captchaService;
    private final LzyCloudOAuth2ExceptionTranslator lzyCloudOAuth2ExceptionTranslator;
    @Value("${captcha.type}")
    private String captchaType;

    @Value("${system.secret-key}")
    private String secretKey;

    @Value("${system.secret-key-salt}")
    private String secretKeySalt;

    /**
     * 客户端信息配置
     * @param clients
     * @throws Exception
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        LzyCloudClientDetailsServiceImpl jdbcClientDetailsService = new LzyCloudClientDetailsServiceImpl(dataSource);
        jdbcClientDetailsService.setFindClientDetailsSql(AuthConstant.FIND_CLIENT_DETAILS_SQL);
        jdbcClientDetailsService.setSelectClientDetailsSql(AuthConstant.SELECT_CLIENT_DETAILS_SQL);
        clients.withClientDetails(jdbcClientDetailsService);
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     *
     * @param endpoints 端点
     * @throws Exception 异常
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 增强令牌
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        // 获取自定义tokenGranter
        TokenGranter tokenGranter = LzyCloudTokenGranter.getTokenGranter(authenticationManager, endpoints, redisTemplate,
                userFeign, captchaService, userDetailsService, captchaType, secretKey, secretKeySalt);


        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(userDetailsService)
                .tokenGranter(tokenGranter)
                .tokenServices(createDefaultTokenServices(endpoints))
                /**
                 *
                 * refresh_token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                 * 1.重复使用：access_token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                 * 2.非重复使用：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新而无需失效再次登录
                 */
                .reuseRefreshTokens(false)
                //自定义异常返回消息
                .exceptionTranslator(lzyCloudOAuth2ExceptionTranslator);
    }

    /**
     * 允许表单认证
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                new ClassPathResource("lzycloud.jks"), "qwe22515".toCharArray());
        KeyPair keyPair = factory.getKeyPair(
                "lzycloud", "qwe22515".toCharArray());
        return keyPair;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Primary
    @Bean
    public DefaultTokenServices createDefaultTokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
        LzyCloudTokenServices tokenServices = new LzyCloudTokenServices(redisTemplate);
        tokenServices.setTokenStore(tokenStore());
        // 支持刷新token
        tokenServices.setSupportRefreshToken(true);
        // 是否重复使用RefreshToken
        tokenServices.setReuseRefreshToken(false);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        addUserDetailsService(tokenServices, this.userDetailsService);
        return tokenServices;
    }

    private void addUserDetailsService(DefaultTokenServices tokenServices, UserDetailsService userDetailsService) {
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(
                    userDetailsService));
            tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        }
    }

    /**
     * JWT内容增强
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> map = new HashMap<>(2);
            LzyCloudUserDetails user = (LzyCloudUserDetails) authentication.getUserAuthentication().getPrincipal();
            map.put(TokenConstant.OAUTH_ID, user.getOauthId());
            map.put(TokenConstant.USER_ID, user.getId());
            map.put(TokenConstant.ORGANIZATION_ID, user.getOrganizationId());
            map.put(TokenConstant.ORGANIZATION_NAME, user.getOrganizationName());
            map.put(TokenConstant.ORGANIZATION_IDS, user.getOrganizationIds());
            map.put(TokenConstant.ORGANIZATION_NAMES, user.getOrganizationNames());
            map.put(TokenConstant.ROLE_ID, user.getRoleId());
            map.put(TokenConstant.ROLE_NAME, user.getRoleName());
            map.put(TokenConstant.ROLE_IDS, user.getRoleIds());
            map.put(TokenConstant.ROLE_NAMES, user.getRoleNames());
            map.put(TokenConstant.ACCOUNT, user.getAccount());
            map.put(TokenConstant.REAL_NAME, user.getRealName());
            map.put(TokenConstant.NICK_NAME, user.getNickname());
            map.put(TokenConstant.ROLE_ID_LIST, user.getRoleIdList());
            map.put(TokenConstant.ROLE_KEY_LIST, user.getRoleKeyList());
            map.put(TokenConstant.ORGANIZATION_ID_LIST, user.getOrganizationIdList());
            map.put(TokenConstant.AVATAR, user.getAvatar());
            map.put(TokenConstant.DATA_PERMISSION_TYPE_LIST, user.getDataPermissionTypeList());
            map.put(TokenConstant.PASSWORD, user.getPassword());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }
}
