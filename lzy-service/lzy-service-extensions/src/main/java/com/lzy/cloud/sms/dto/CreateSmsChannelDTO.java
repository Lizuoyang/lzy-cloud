package com.lzy.cloud.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
public class CreateSmsChannelDTO{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道编码")
    @NotBlank(message="渠道编码不能为空")
    @Length(min=1,max=32)
    private String channelCode;

    @ApiModelProperty(value = "渠道名称")
    @NotBlank(message="渠道名称不能为空")
    @Length(min=1,max=32)
    private String channelName;

    @ApiModelProperty(value = "SecretId")
    @NotBlank(message="SecretId不能为空")
    @Length(min=1,max=100)
    private String secretId;

    @ApiModelProperty(value = "SecretKey")
    @NotBlank(message="SecretKey不能为空")
    @Length(min=1,max=100)
    private String secretKey;

    @ApiModelProperty(value = "RegionId")
    @NotBlank(message="RegionId不能为空")
    @Length(min=1,max=255)
    private String regionId;

    @ApiModelProperty(value = "渠道状态")
    @NotBlank(message="渠道状态不能为空")
    @Min(0)
    @Max(2147483647L)
    @Length(min=1,max=3)
    private Integer channelStatus;

    @ApiModelProperty(value = "短信数量")
    @Min(0)
    @Max(9223372036854775807L)
    @Length(min=1,max=19)
    private Long smsQuantity;

    @ApiModelProperty(value = "有效期")
    @Length(min=1,max=19)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime smsValidity;

    @ApiModelProperty(value = "描述")
    @NotBlank(message="描述不能为空")
    @Length(min=1,max=255)
    private String comments;
}
