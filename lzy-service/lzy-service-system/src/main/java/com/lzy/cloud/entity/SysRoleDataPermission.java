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
 * 角色和数据权限关联表
 * </p>
 *
 * @author lzy
 * @since 2023-05-11
 */
@Data
@TableName("t_sys_role_data_permission")
@ApiModel(value = "SysRoleDataPermission对象", description = "角色和数据权限关联表")
public class SysRoleDataPermission extends BaseEntity implements Serializable {

    public static final LambdaQueryWrapper<SysRoleDataPermission> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<SysRoleDataPermission> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<SysRoleDataPermission> qw(){
        return new QueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "资源id")
    private Long dataPermissionId;

}
