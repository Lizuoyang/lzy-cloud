package com.lzy.cloud.option;

/**
 * Quoted from @see https://github.com/chenggangpro/spring-cloud-gateway-plugin
 * The LogType Of Plugin Filter
 * @author 2428
 * @date 2022/06/27
 */
public enum GatewayLogTypeEnum {

    /**
     * Gateway LogType all
     */
    ALL("all"),
    /**
     * Gateway LogType configure
     */
    CONFIGURE("configure"),
    /**
     * Gateway LogType service
     */
    SERVICE("service"),
    /**
     * Gateway LogType path
     */
    PATH("path"),
    ;

    private String type;

    GatewayLogTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
