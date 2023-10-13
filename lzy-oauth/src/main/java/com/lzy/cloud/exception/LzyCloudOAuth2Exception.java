package com.lzy.cloud.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;


/**
 * 自定义Oauth异常拦截处理器
 *
 * @author 2428
 * @date 2022/06/21
 */
@JsonSerialize(using = LzyCloudOAuth2ExceptionSerializer.class)
public class LzyCloudOAuth2Exception extends OAuth2Exception {

    public LzyCloudOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public LzyCloudOAuth2Exception(String msg) {
        super(msg);
    }

}
