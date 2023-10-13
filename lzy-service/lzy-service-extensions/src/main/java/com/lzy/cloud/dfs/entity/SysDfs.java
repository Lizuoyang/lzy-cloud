package com.lzy.cloud.dfs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.platform.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 分布式存储配置表
 * </p>
 *
 * @author lizuoyang
 * @since 2023-08-03
 */
@Data
@TableName("t_sys_dfs")
@ApiModel(value = "SysDfs对象", description = "分布式存储配置表")
public class SysDfs extends BaseEntity{

    public static final LambdaQueryWrapper<SysDfs> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final QueryWrapper<SysDfs> qw(){
        return new QueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分布式存储分类")
    @TableField("dfs_type")
    private String dfsType;

    @ApiModelProperty(value = "分布式存储编号")
    @TableField("dfs_code")
    private String dfsCode;

    @ApiModelProperty(value = "文件访问地址前缀")
    @TableField("access_url_prefix")
    private String accessUrlPrefix;

    @ApiModelProperty(value = "分布式存储上传地址")
    @TableField("upload_url")
    private String uploadUrl;

    @ApiModelProperty(value = "空间名称")
    @TableField("bucket")
    private String bucket;

    @ApiModelProperty(value = "应用ID")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "区域")
    @TableField("region")
    private String region;

    @ApiModelProperty(value = "accessKey")
    @TableField("access_key")
    private String accessKey;

    @ApiModelProperty(value = "secretKey")
    @TableField("secret_key")
    private String secretKey;

    @ApiModelProperty(value = "是否默认存储 0否，1是")
    @TableField("dfs_default")
    private Integer dfsDefault;

    @ApiModelProperty(value = "状态 0禁用，1 启用")
    @TableField("dfs_status")
    private Integer dfsStatus;

    @ApiModelProperty(value = "访问控制 0私有，1公开")
    @TableField("access_control")
    private Integer accessControl;

    @ApiModelProperty(value = "备注")
    @TableField("comments")
    private String comments;
}
