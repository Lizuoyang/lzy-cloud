
package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 更新角色
 * </p>
 *
 * @author 2428
 * @date 2022/06/21
 */
@Data
@ApiModel(value = "UpdateRole对象", description = "更新角色时的对象")
public class UpdateRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色标识")
    private String roleKey;

    @ApiModelProperty(value = "角色级别")
    private Integer roleLevel;

    @ApiModelProperty(value = "1有效，0禁用")
    private Integer roleStatus;

    @ApiModelProperty(value = "角色数据权限")
    private String dataPermissionType;

    @ApiModelProperty(value = "备注")
    private String comments;

}
