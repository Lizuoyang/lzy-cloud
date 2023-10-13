package com.lzy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dto.QueryUserDTO;
import com.lzy.cloud.entity.User;
import com.lzy.cloud.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户列表
     * @param page
     * @param user
     * @return
     */
    Page<UserInfo> selectUserList(Page<UserInfo> page, @Param("user") QueryUserDTO user);


    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return {@link List}<{@link UserInfo}>
     */
    List<UserInfo> selectUserList(@Param("user") QueryUserDTO user);

    /**
     * 查询用户信息
     * @param user
     * @return
     */
    UserInfo queryUserInfo(@Param("user") User user);

    /**
     * 查询已存在的用户，用户名、昵称、邮箱、手机号有任一重复即视为用户已存在，真实姓名是可以重复的。
     * @param user
     * @return
     */
    List<User> queryExistUser(@Param("user") User user);
}
