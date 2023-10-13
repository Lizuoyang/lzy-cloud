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
 * 分布式存储文件记录表
 * </p>
 *
 * @author lizuoyang
 * @since 2023-08-03
 */
@Data
@TableName("t_sys_dfs_file")
@ApiModel(value = "SysDfsFile对象", description = "分布式存储文件记录表")
public class SysDfsFile extends BaseEntity{
    public static final LambdaQueryWrapper<SysDfsFile> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final QueryWrapper<SysDfsFile> qw(){
        return new QueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分布式存储配置id")
    @TableField("dfs_id")
    private Long dfsId;

    @ApiModelProperty(value = "文件访问地址")
    @TableField("access_url")
    private String accessUrl;

    @ApiModelProperty(value = "原文件名")
    @TableField("original_name")
    private String originalName;

    @ApiModelProperty(value = "存储文件名")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @TableField("file_extension")
    private String fileExtension;

    @ApiModelProperty(value = "文件大小")
    @TableField("file_size")
    private Long fileSize;

    @ApiModelProperty(value = "状态 0上传成功失败，1 上传成功")
    @TableField("file_status")
    private Integer fileStatus;

    @ApiModelProperty(value = "备注")
    @TableField("comments")
    private String comments;
}
