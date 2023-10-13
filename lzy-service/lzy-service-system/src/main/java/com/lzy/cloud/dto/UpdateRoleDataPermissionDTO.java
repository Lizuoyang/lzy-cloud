package com.lzy.cloud.dto;

import com.lzy.cloud.entity.SysRoleDataPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 角色和数据权限关联表
 * </p>
 *
 * @author lzy
 * @since 2023-05-14
 */
@Data
@ApiModel(value="RoleDataPermission对象", description="角色和数据权限关联表")
public class UpdateRoleDataPermissionDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "资源id")
    private Long dataPermissionId;

    @ApiModelProperty(value = "添加的角色列表")
    private List<SysRoleDataPermission> addRoles;

    @ApiModelProperty(value = "删除的角色列表")
    private List<SysRoleDataPermission> delRoles;


}
