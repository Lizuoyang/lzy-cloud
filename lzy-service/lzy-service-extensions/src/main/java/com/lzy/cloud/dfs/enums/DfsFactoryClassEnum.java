package com.lzy.cloud.dfs.enums;


/**
 * 分布式存储工厂类枚举 ，因dfs表存的是数据字典表的id，这里省一次数据库查询，所以就用数据字典的id
 *
 * @author lizuoyang
 * @date 2023/08/03
 */
public enum DfsFactoryClassEnum {

    /**
     * MINIO MINIO
     */
    MINIO("MINIO", "com.lzy.cloud.dfs.factory.DfsMinioFactory"),

    /**
     * 七牛云Kodo QINIUYUN_KODO
     */
    QI_NIU("QINIUYUN_KODO", "com.lzy.cloud.dfs.factory.DfsQiniuFactory"),

    /**
     * 阿里云OSS ALIYUN_OSS
     */
    ALI_YUN("ALIYUN_OSS", "com.lzy.cloud.dfs.factory.DfsAliyunFactory"),
    ;

    public String code;

    public String value;

    DfsFactoryClassEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValue(String code) {
        DfsFactoryClassEnum[] smsFactoryClassEnums = values();
        for (DfsFactoryClassEnum smsFactoryClassEnum : smsFactoryClassEnums) {
            if (smsFactoryClassEnum.getCode().equals(code)) {
                return smsFactoryClassEnum.getValue();
            }
        }
        return null;
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
