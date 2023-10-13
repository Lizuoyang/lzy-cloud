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
 * 角色表
 * </p>
 *
 * @author 2428
 * @date 2022/06/21
 */
@Data
@TableName("t_sys_role")
@ApiModel(value="Role对象", description="角色表")
public class Role extends BaseEntity {

    public static final LambdaQueryWrapper<Role> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<Role> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<Role> qw(){
        return new QueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value = "角色标识")
    @TableField("role_key")
    private String roleKey;

    @ApiModelProperty(value = "角色级别")
    @TableField("role_level")
    private Integer roleLevel;

    @ApiModelProperty(value = "1有效，0禁用")
    @TableField("role_status")
    private Integer roleStatus;

    @ApiModelProperty(value = "角色数据权限")
    @TableField("data_permission_type")
    private String dataPermissionType;

    @ApiModelProperty(value = "备注")
    @TableField("comments")
    private String comments;

}
