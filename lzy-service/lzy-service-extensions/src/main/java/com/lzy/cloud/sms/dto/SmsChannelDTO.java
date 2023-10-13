package com.lzy.cloud.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * 短信渠道表
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
@Data
@ApiModel(value="SmsChannelDTO对象", description="短信渠道表")
public class SmsChannelDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "渠道编码")
    private String channelCode;

    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    @ApiModelProperty(value = "SecretId")
    private String secretId;

    @ApiModelProperty(value = "SecretKey")
    private String secretKey;

    @ApiModelProperty(value = "RegionId")
    private String regionId;

    @ApiModelProperty(value = "渠道状态")
    private Integer channelStatus;

    @ApiModelProperty(value = "短信数量")
    private Long smsQuantity;

    @ApiModelProperty(value = "有效期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime smsValidity;

    @ApiModelProperty(value = "描述")
    private String comments;
}
