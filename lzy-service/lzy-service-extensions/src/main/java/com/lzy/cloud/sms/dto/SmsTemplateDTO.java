package com.lzy.cloud.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 短信配置表
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
@Data
@ApiModel(value="SmsTemplateDTO对象", description="短信配置表")
public class SmsTemplateDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "短信渠道")
    private Long channelId;

    @ApiModelProperty(value = "短信编码")
    private String smsCode;

    @ApiModelProperty(value = "短信名称")
    private String smsName;

    @ApiModelProperty(value = "模板ID")
    private String templateId;

    @ApiModelProperty(value = "短信签名")
    private String signName;

    @ApiModelProperty(value = "短信状态")
    private Integer templateStatus;

    @ApiModelProperty(value = "短信类型")
    private Integer templateType;

    @ApiModelProperty(value = "缓存key")
    private String cacheCodeKey;

    @ApiModelProperty(value = "缓存有效期")
    private Long cacheTimeOut;

    @ApiModelProperty(value = "有效期单位")
    private String cacheTimeOutUnit;

    @ApiModelProperty(value = "发送次数限制")
    private Long sendTimesLimit;

    @ApiModelProperty(value = "限制时间间隔")
    private Long sendTimesLimitPeriod;

    @ApiModelProperty(value = "时间间隔单位")
    private String sendTimesLimitPeriodUnit;

    @ApiModelProperty(value = "描述")
    private String comments;

    @ApiModelProperty(value = "渠道编码")
    private String channelCode;

    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    @ApiModelProperty(value = "短信发送的key id")
    private String secretId;

    @ApiModelProperty(value = "短信发送的秘钥")
    private String secretKey;

    @ApiModelProperty(value = "regionId")
    private String regionId;

    @ApiModelProperty(value = "渠道状态 1有效 0禁用")
    private Integer channelStatus;
}
