package com.lzy.cloud.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 短信渠道表
 * </p>
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
@Data
@ApiModel(value="SmsChannel对象", description="短信渠道表")
public class QuerySmsChannelDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    private String channelCode;

    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    @ApiModelProperty(value = "开始时间")
    private String beginDateTime;

    @ApiModelProperty(value = "结束时间")
    private String endDateTime;

}
