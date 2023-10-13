package com.lzy.cloud.enums;

/**
 * 登录类型枚举
 *
 * @author 2428
 * @date 2022/06/21
 */

public enum AuthEnum {

    /**
     * 密码登录
     */
    PASSWORD("password", "密码登录"),

    /**
     * 验证码登录
     */
    CAPTCHA("captcha", "验证码登录"),

    /**
     * 二维码登录
     */
    QR("qr", "二维码登录");

    public String code;

    public String value;

    AuthEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
