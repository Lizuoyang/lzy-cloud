package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 组织创建
 * </p>
 *
 * @author lzy
 * @since 2023-05-11
 */
@Data
@ApiModel(value = "CreateOrganization对象", description = "创建组织机构时的对象")
public class CreateOrganizationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty(value = "所有上级组织id的集合")
    private String ancestors;

    @ApiModelProperty(value = "组织类型：1总公司，2分公司，3事业部")
    private String organizationType;

    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    @ApiModelProperty(value = "组织编码")
    private String organizationKey;

    @ApiModelProperty(value = "组织图标")
    private String organizationIcon;

    @ApiModelProperty(value = "组织级别（排序）")
    private Integer organizationLevel;

    @ApiModelProperty(value = "1有效，0禁用")
    private Integer organizationStatus;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "街道详细地址")
    private String street;

    @ApiModelProperty(value = "地区数组")
    private List<String> areas;

    @ApiModelProperty(value = "描述")
    private String comments;

}
