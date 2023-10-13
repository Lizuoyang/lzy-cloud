package com.lzy.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lzy.platform.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 组织表
 * </p>
 *
 * @author lzy
 * @since 2023-05-08
 */
@Data
@TableName("t_sys_organization")
@ApiModel(value = "SysOrganization对象", description = "组织表")
public class SysOrganization extends BaseEntity implements Serializable {

    public static final LambdaQueryWrapper<SysOrganization> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<SysOrganization> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<SysOrganization> qw(){
        return new QueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父组织id")
    private Long parentId;

    @ApiModelProperty(value = "所有上级组织id的集合，便于机构查找")
    private String ancestors;

    @ApiModelProperty(value = "组织类型：1：事业部  2：机构  3：盐城")
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

    @ApiModelProperty(value = "街道")
    private String street;

    @ApiModelProperty(value = "描述")
    private String comments;

    /**
     * 是否是叶子节点(查询时，如果此值为 1，则表示只查询子节点)
     */
    @TableField(exist = false)
    private Integer isLeaf;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysOrganization> children;
}
