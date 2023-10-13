package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色和数据权限关联表
 * </p>
 *
 * @author lzy
 * @since 2023-05-14
 */
@Data
@ApiModel(value="RoleDataPermissionDTO对象", description="角色和数据权限关联表")
public class RoleDataPermissionDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "资源id")
    private Long dataPermissionId;


}
