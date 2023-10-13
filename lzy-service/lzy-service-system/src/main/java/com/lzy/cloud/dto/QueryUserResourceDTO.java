
package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 查询用户权限
 * </p>
 *
 * @author 2428
 * @date 2022/06/21
 */
@Data
@ApiModel(value = "QueryUserResourceDTO", description = "查询用户权限")
public class QueryUserResourceDTO implements Serializable
{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "资源权限类型")
    private List<String> resourceTypeList;

}
