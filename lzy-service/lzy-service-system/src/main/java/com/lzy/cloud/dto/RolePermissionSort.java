package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色权限类型排序
 *
 * @author 2428
 * @date 2022/06/21
 */
@Data
public class RolePermissionSort {

    @ApiModelProperty(value = "序号")
    private int index;

    @ApiModelProperty(value = "数据权限类型")
    private String dataPermissionType;
}
