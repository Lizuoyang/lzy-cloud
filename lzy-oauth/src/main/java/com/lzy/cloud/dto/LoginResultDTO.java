package com.lzy.cloud.dto;

import lombok.Data;

/**
 * 登录
 *
 * @author 2428
 * @version V1.0
 * @date 2022/06/23
 */
@Data
public class LoginResultDTO {

    private boolean success;

    private String message;

    private String targetUrl;
}
