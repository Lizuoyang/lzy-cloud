package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询角色信息
 *
 * @author lizuoyang
 * @date 2023/01/10
 */
@Data
@ApiModel(value = "QueryRoleDTO对象", description = "角色查询")
public class QueryRoleDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

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

}
