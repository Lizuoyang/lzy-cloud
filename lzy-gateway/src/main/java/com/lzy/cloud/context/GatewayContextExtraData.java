package com.lzy.cloud.context;

/**
 * Quoted from @see https://github.com/chenggangpro/spring-cloud-gateway-plugin
 * @author 2428
 * @date 2022/06/27
 */
public interface GatewayContextExtraData<T> {

    /**
     * get context extra data
     * @return
     */
    T getData();
}
