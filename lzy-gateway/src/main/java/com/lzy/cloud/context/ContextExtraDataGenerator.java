package com.lzy.cloud.context;

import org.springframework.web.server.ServerWebExchange;

/**
 * Quoted from @see https://github.com/chenggangpro/spring-cloud-gateway-plugin
 * @author 2428
 * @date 2022/06/27
 */
public interface ContextExtraDataGenerator<T> {

    /**
     * generate context extra data
     * @param serverWebExchange
     * @return
     */
    GatewayContextExtraData<T> generateContextExtraData(ServerWebExchange serverWebExchange);

}
