package com.lzy.cloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzy.cloud.dto.CreateUserDTO;
import com.lzy.cloud.dto.QueryUserDTO;
import com.lzy.cloud.dto.UpdateUserDTO;
import com.lzy.cloud.entity.User;
import com.lzy.cloud.entity.UserInfo;

import java.util.List;

/**
 * @ClassName: IUserService
 * @Description: 用户相关操接口
 */
public interface IUserService extends IService<User> {

    /**
     * 创建用户，成功后将id和对象返回
     *
     * @param user
     * @param
     * @return
     */
    CreateUserDTO createUser(CreateUserDTO user);

    /**
     * 更新用户
     * 、     * @param user
     * @param
     * @return
     */
    boolean updateUser(UpdateUserDTO user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    boolean deleteUser(Long userId);

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    boolean batchDeleteUser(List<Long> userIds);

    /**
     * 根据用户名查询用户
     *
     * @param userAccount 用户账号
     * @return 用户
     */
    User queryUserByAccount(String userAccount);

    /**
     * 查询已存在的用户，用户名、昵称、邮箱、手机号有任一重复即视为用户已存在，真实姓名是可以重复的。
     * @param user
     * @return
     */
    public Boolean checkUserExist(User user);

    /**
     * 分页查询用户
     * @param page
     * @param user
     * @return
     */
    Page<UserInfo> selectUserList(Page<UserInfo> page, QueryUserDTO user);

    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return {@link List}<{@link UserInfo}>
     */
    List<UserInfo> selectUserList(QueryUserDTO user);

    /**
     * 查询用户详细信息
     * @param user
     * @return
     */
    UserInfo queryUserInfo(User user);
}
