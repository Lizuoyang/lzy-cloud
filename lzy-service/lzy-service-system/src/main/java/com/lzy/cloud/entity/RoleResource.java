package com.lzy.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lzy.platform.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 角色和权限关联表
 * </p>
 *
 * @author 2428
 * @date 2022/06/21
 */
@Data
@TableName("t_sys_role_resource")
@ApiModel(value="RoleResource对象", description="角色和权限关联表")
public class RoleResource extends BaseEntity {

    public static final LambdaQueryWrapper<RoleResource> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<RoleResource> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<RoleResource> qw(){
        return new QueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "资源id")
    @TableField("resource_id")
    private Long resourceId;

}
