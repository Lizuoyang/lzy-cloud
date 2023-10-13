package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 更新数据权限
 * </p>
 *
 * @author lzy
 * @since 2023-05-10
 */
@Data
@ApiModel(value="DataPermissionUser对象", description="")
public class UpdateDataPermissionUserDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "机构id")
    private Long organizationId;

    @ApiModelProperty(value = "状态 0禁用，1 启用,")
    private Integer status;

    @ApiModelProperty(value = "需要添加的机构权限")
    private List<Long> addDataPermissions;

    @ApiModelProperty(value = "需要删除的机构权限")
    private List<Long> removeDataPermissions;


}
