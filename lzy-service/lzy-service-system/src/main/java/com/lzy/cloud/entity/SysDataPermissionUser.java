package com.lzy.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lzy.platform.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 数据权限多部门
 * </p>
 *
 * @author lzy
 * @since 2023-05-10
 */
@Data
@TableName("t_sys_data_permission_user")
@ApiModel(value = "SysDataPermissionUser对象", description = "数据权限多部门")
public class SysDataPermissionUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final LambdaQueryWrapper<SysDataPermissionUser> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<SysDataPermissionUser> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<SysDataPermissionUser> qw(){
        return new QueryWrapper<>();
    }

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "机构id")
    private Long organizationId;

    @ApiModelProperty(value = "状态 0禁用，1 启用,")
    private Integer status;

}
