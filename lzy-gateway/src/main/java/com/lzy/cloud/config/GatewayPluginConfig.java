package com.lzy.cloud.config;

import com.lzy.cloud.context.ContextExtraDataGenerator;
import com.lzy.cloud.filter.GatewayRequestContextFilter;
import com.lzy.cloud.filter.GatewayResponseContextFilter;
import com.lzy.cloud.filter.RemoveGatewayContextFilter;
import com.lzy.cloud.filter.RequestLogFilter;
import com.lzy.cloud.properties.GatewayPluginProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quoted from @see https://github.com/chenggangpro/spring-cloud-gateway-plugin
 *
 * Gateway Plugin Config
 * @author 2428
 * @date 2022/06/27
 */
@Slf4j
@Configuration
public class GatewayPluginConfig {

    @Bean
    @ConditionalOnMissingBean(GatewayPluginProperties.class)
    @ConfigurationProperties(GatewayPluginProperties.GATEWAY_PLUGIN_PROPERTIES_PREFIX)
    public GatewayPluginProperties gatewayPluginProperties(){
        return new GatewayPluginProperties();
    }

    @Bean
    @ConditionalOnBean(GatewayPluginProperties.class)
    @ConditionalOnMissingBean(GatewayRequestContextFilter.class)
    @ConditionalOnProperty(prefix = GatewayPluginProperties.GATEWAY_PLUGIN_PROPERTIES_PREFIX, value = { "enable", "requestLog" },havingValue = "true")
    public GatewayRequestContextFilter gatewayContextFilter(@Autowired GatewayPluginProperties gatewayPluginProperties , @Autowired(required = false) ContextExtraDataGenerator contextExtraDataGenerator){
        GatewayRequestContextFilter gatewayContextFilter = new GatewayRequestContextFilter(gatewayPluginProperties, contextExtraDataGenerator);
        log.debug("Load GatewayContextFilter Config Bean");
        return gatewayContextFilter;
    }

    @Bean
    @ConditionalOnMissingBean(GatewayResponseContextFilter.class)
    @ConditionalOnProperty(prefix = GatewayPluginProperties.GATEWAY_PLUGIN_PROPERTIES_PREFIX, value = { "enable", "responseLog" }, havingValue = "true")
    public GatewayResponseContextFilter responseLogFilter(){
        GatewayResponseContextFilter responseLogFilter = new GatewayResponseContextFilter();
        log.debug("Load Response Log Filter Config Bean");
        return responseLogFilter;
    }

    @Bean
    @ConditionalOnBean(GatewayPluginProperties.class)
    @ConditionalOnMissingBean(RemoveGatewayContextFilter.class)
    @ConditionalOnProperty(prefix = GatewayPluginProperties.GATEWAY_PLUGIN_PROPERTIES_PREFIX, value = { "enable" }, havingValue = "true")
    public RemoveGatewayContextFilter removeGatewayContextFilter(){
        RemoveGatewayContextFilter gatewayContextFilter = new RemoveGatewayContextFilter();
        log.debug("Load RemoveGatewayContextFilter Config Bean");
        return gatewayContextFilter;
    }

    @Bean
    @ConditionalOnMissingBean(RequestLogFilter.class)
    @ConditionalOnProperty(prefix = GatewayPluginProperties.GATEWAY_PLUGIN_PROPERTIES_PREFIX, value = { "enable" },havingValue = "true")
    public RequestLogFilter requestLogFilter(@Autowired GatewayPluginProperties gatewayPluginProperties){
        RequestLogFilter requestLogFilter = new RequestLogFilter(gatewayPluginProperties);
        log.debug("Load Request Log Filter Config Bean");
        return requestLogFilter;
    }

}
