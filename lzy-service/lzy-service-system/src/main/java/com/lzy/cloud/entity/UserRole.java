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


/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author 2428
 * @date 2022/06/21
 */
@Data
@TableName("t_sys_user_role")
@ApiModel(value="UserRole对象", description="用户和角色关联表")
public class UserRole extends BaseEntity {

    public static final LambdaQueryWrapper<UserRole> lqw(){
        return new LambdaQueryWrapper<>();
    }

    public static final LambdaUpdateWrapper<UserRole> luw(){
        return new LambdaUpdateWrapper<>();
    }

    public static final QueryWrapper<UserRole> qw(){
        return new QueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;
}
