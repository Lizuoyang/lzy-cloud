package com.lzy.cloud.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.lzy.platform.mybatis.entity.BaseEntity;
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
@EqualsAndHashCode(callSuper = true)
@TableName("t_sms_channel")
@ApiModel(value = "SmsChannel对象", description = "短信渠道表")
public class SmsChannel extends BaseEntity {
    public static final LambdaQueryWrapper<SmsChannel> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<SmsChannel> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<SmsChannel> qw(){
        return new QueryWrapper<>();
    }

    public static final MPJLambdaWrapper<SmsChannel> mpj_lqw(){
        return new MPJLambdaWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "渠道编码")
    @TableField("channel_code")
    private String channelCode;

    @ApiModelProperty(value = "渠道名称")
    @TableField("channel_name")
    private String channelName;

    @ApiModelProperty(value = "短信发送的key id")
    @TableField("secret_id")
    private String secretId;

    @ApiModelProperty(value = "短信发送的秘钥")
    @TableField("secret_key")
    private String secretKey;

    @ApiModelProperty(value = "regionId仅阿里云需要")
    @TableField("region_id")
    private String regionId;

    @ApiModelProperty(value = "渠道状态 1有效 0禁用")
    @TableField("channel_status")
    private Integer channelStatus;

    @ApiModelProperty(value = "渠道下可发送的短信数量")
    @TableField("sms_quantity")
    private Long smsQuantity;

    @ApiModelProperty(value = "短信可发送的有效期")
    @TableField("sms_validity")
    private LocalDateTime smsValidity;

    @ApiModelProperty(value = "描述")
    @TableField("comments")
    private String comments;


}
