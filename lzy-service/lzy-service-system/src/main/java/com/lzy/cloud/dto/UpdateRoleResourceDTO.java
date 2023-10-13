package com.lzy.cloud.dto;


import com.lzy.cloud.entity.RoleResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新角色权限
 *
 * @author 2428
 * @date 2022/06/21
 */
@Data
@ApiModel(value = "UpdateRoleResource对象", description = "更新角色权限时的对象")
public class UpdateRoleResourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "需要操作的角色id")
    private Long roleId;

    @ApiModelProperty(value = "添加的资源列表")
    private List<RoleResource> addResources;

    @ApiModelProperty(value = "删除的资源列表")
    private List<RoleResource> delResources;

}
