package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 组织查询
 * </p>
 *
 * @author lzy
 * @since 2023-05-12
 */
@Data
@ApiModel(value = "QueryOrganizationDTO", description = "查询组织机构时的对象")
public class QueryOrganizationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty(value = "组织类型：1总公司，2分公司，3事业部")
    private String organizationType;

    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    @ApiModelProperty(value = "组织编码")
    private String organizationKey;

    @ApiModelProperty(value = "组织级别（排序）")
    private Integer organizationLevel;

    @ApiModelProperty(value = "1有效，0禁用")
    private Integer organizationStatus;

}
