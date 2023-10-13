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
 * 可以给组织权限，在该组织下的所有用户都有此权限
 * </p>
 *
 * @author lzy
 * @since 2023-05-08
 */
@Data
@TableName("t_sys_organization_role")
@ApiModel(value = "SysOrganizationRole对象", description = "可以给组织权限，在该组织下的所有用户都有此权限")
public class SysOrganizationRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final LambdaQueryWrapper<SysOrganizationRole> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<SysOrganizationRole> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<SysOrganizationRole> qw(){
        return new QueryWrapper<>();
    }

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "组织机构id")
    private Long organizationId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

}
