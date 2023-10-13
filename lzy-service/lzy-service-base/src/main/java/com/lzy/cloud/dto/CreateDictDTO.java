package com.lzy.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author lzy
 * @since 2023-03-27
 */
@Data
@ApiModel(value = "Dict对象", description = "数据字典")
public class CreateDictDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典类型")
    private Long parentId = 0L;

    @ApiModelProperty(value = "所有上级id的集合")
    private String ancestors;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典值")
    private String dictCode;

    @ApiModelProperty(value = "排序")
    private Integer dictOrder;

    @ApiModelProperty(value = "1有效，0禁用")
    private Integer dictStatus;

    @ApiModelProperty(value = "描述信息")
    private String comments;

}
