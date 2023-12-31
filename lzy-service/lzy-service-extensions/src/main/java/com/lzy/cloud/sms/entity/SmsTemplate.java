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


/**
 * 短信配置表
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sms_template")
@ApiModel(value = "SmsTemplate对象", description = "短信配置表")
public class SmsTemplate extends BaseEntity {
    public static final LambdaQueryWrapper<SmsTemplate> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<SmsTemplate> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<SmsTemplate> qw(){
        return new QueryWrapper<>();
    }

    public static final MPJLambdaWrapper<SmsTemplate> mpj_lqw(){
        return new MPJLambdaWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "短信渠道id")
    @TableField("channel_id")
    private Long channelId;

    @ApiModelProperty(value = "短信编码")
    @TableField("sms_code")
    private String smsCode;

    @ApiModelProperty(value = "短信名称")
    @TableField("sms_name")
    private String smsName;

    @ApiModelProperty(value = "短信模板ID")
    @TableField("template_id")
    private String templateId;

    @ApiModelProperty(value = "短信签名")
    @TableField("sign_name")
    private String signName;

    @ApiModelProperty(value = "短信状态 1有效 0禁用")
    @TableField("template_status")
    private Integer templateStatus;

    @ApiModelProperty(value = "短信类型 1验证码 2普通短信")
    @TableField("template_type")
    private Integer templateType;

    @ApiModelProperty(value = "缓存key")
    @TableField("cache_code_key")
    private String cacheCodeKey;

    @ApiModelProperty(value = "缓存有效期 值")
    @TableField("cache_time_out")
    private Long cacheTimeOut;

    @ApiModelProperty(value = "缓存有效期 单位")
    @TableField("cache_time_out_unit")
    private String cacheTimeOutUnit;

    @ApiModelProperty(value = "发送次数限制")
    @TableField("send_times_limit")
    private Long sendTimesLimit;

    @ApiModelProperty(value = "限制时间间隔")
    @TableField("send_times_limit_period")
    private Long sendTimesLimitPeriod;

    @ApiModelProperty(value = "限制时间间隔 单位")
    @TableField("send_times_limit_period_unit")
    private String sendTimesLimitPeriodUnit;

    @ApiModelProperty(value = "描述")
    @TableField("comments")
    private String comments;


}
