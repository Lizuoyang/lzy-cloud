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
 * 可以给用户权限
 * </p>
 *
 * @author lzy
 * @since 2023-05-08
 */
@Data
@TableName("t_sys_organization_user")
@ApiModel(value = "SysOrganizationUser对象", description = "可以给用户权限")
public class SysOrganizationUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final LambdaQueryWrapper<SysOrganizationUser> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<SysOrganizationUser> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<SysOrganizationUser> qw(){
        return new QueryWrapper<>();
    }

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "机构id")
    private Long organizationId;

    @ApiModelProperty(value = "用户id")
    private Long userId;
}
