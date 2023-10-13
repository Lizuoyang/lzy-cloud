package com.lzy.cloud.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.dto.CreateUserDTO;
import com.lzy.cloud.dto.QueryUserDTO;
import com.lzy.cloud.dto.QueryUserResourceDTO;
import com.lzy.cloud.dto.UpdateUserDTO;
import com.lzy.cloud.entity.*;
import com.lzy.cloud.enums.ResourceEnum;
import com.lzy.cloud.mapper.UserMapper;
import com.lzy.cloud.service.*;
import com.lzy.platform.base.constant.AuthConstant;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.base.enums.ResultCodeEnum;
import com.lzy.platform.base.exception.BusinessException;
import com.lzy.platform.base.utils.BeanCopierUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户相关操作接口实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    private final IUserRoleService userRoleService;

    private final IResourceService resourceService;

    private final ISysOrganizationuserService organizationUserService;

    private final RedisTemplate redisTemplate;

    @Value("${system.defaultPwd}")
    private String defaultPwd;

    @Value("${system.defaultUserStatus}")
    private int defaultUserStatus;

    @Override
    public Page<UserInfo> selectUserList(Page<UserInfo> page, QueryUserDTO user) {
        Page<UserInfo> pageUserInfo = userMapper.selectUserList(page, user);
        return pageUserInfo;
    }

    @Override
    public List<UserInfo> selectUserList(QueryUserDTO user) {
        List<UserInfo> userInfos = userMapper.selectUserList(user);
        return userInfos;
    }

    /**
     * 新增用户，成功后将id和对象返回
     * @param user
     * @return
     */
    @Override
    public CreateUserDTO createUser(CreateUserDTO user) {

        User userEntity = BeanCopierUtils.copyByClass(user, User.class);
        // 查询已存在的用户，用户名、昵称、邮箱、手机号有任一重复即视为用户已存在，真实姓名是可以重复的。
        List<User> userList = userMapper.queryExistUser(userEntity);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new BusinessException("账号已经存在");
        }

        // 如果为空时设置默认角色
        Long roleId = user.getRoleId();
        List<Long> roleIds = user.getRoleIds();

        // 设置默认状态
        if (null == userEntity.getStatus())
        {
            userEntity.setStatus(defaultUserStatus);
        }

        // 处理前端传过来的省市区
        userEntity = resolveAreas(userEntity, user.getAreas());

        String pwd = userEntity.getPassword();
        if (StringUtils.isEmpty(pwd)) {
            // 默认密码，配置文件配置
            pwd = defaultPwd;
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String cryptPwd = passwordEncoder.encode(AuthConstant.BCRYPT + userEntity.getAccount() +  DigestUtils.md5DigestAsHex(pwd.getBytes()));
        userEntity.setPassword(cryptPwd);
        // 保存用户
        boolean result = this.save(userEntity);
        if (result) {

            if (user.getOrganizationId() != null) {
                //保存用户和组织机构的关系
                Long organizationId =  user.getOrganizationId();
                SysOrganizationUser organizationUser = new SysOrganizationUser();
                organizationUser.setUserId(userEntity.getId());
                organizationUser.setOrganizationId(organizationId);
                organizationUserService.save(organizationUser);
            }

            //保存用户多个角色信息
            List<UserRole> userRoleList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(roleIds))
            {
                for (Long role : roleIds)
                {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userEntity.getId());
                    userRole.setRoleId(role);
                    userRoleList.add(userRole);
                }
                userRoleService.saveBatch(userRoleList);
            }

            //保存用户单个角色信息
            if(null != roleId)
            {
                UserRole userRole = new UserRole();
                userRole.setUserId(userEntity.getId());
                userRole.setRoleId(roleId);
                userRoleService.save(userRole);
            }
            user.setId(userEntity.getId());
        }
        return user;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    public boolean updateUser(UpdateUserDTO user) {

        User userEntity = BeanCopierUtils.copyByClass(user, User.class);
        // 查询已存在的用户，用户名、昵称、邮箱、手机号有任一重复即视为用户已存在，真实姓名是可以重复的。
        List<User> userList = userMapper.queryExistUser(userEntity);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new BusinessException("账号已经存在");
        }

        // 处理前端传过来的省市区
        userEntity = resolveAreas(userEntity, user.getAreas());

        String pwd = userEntity.getPassword();
        User oldInfo = this.getById(userEntity.getId());

        if (oldInfo == null)
        {
            throw new BusinessException("用户未找到");
        }

        if (!StringUtils.isEmpty(pwd)) {
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            String cryptPwd = passwordEncoder.encode(AuthConstant.BCRYPT + oldInfo.getAccount() +  DigestUtils.md5DigestAsHex(pwd.getBytes()));
            userEntity.setPassword(cryptPwd);
        }
        boolean result = this.updateById(userEntity);

        // 修改用户机构
        if (result && null != user.getOrganizationId())
        {
            LambdaQueryWrapper<SysOrganizationUser> orgUserLambdaQueryWrapper = SysOrganizationUser.lqw();
            orgUserLambdaQueryWrapper.eq(SysOrganizationUser::getUserId, userEntity.getId()).eq(SysOrganizationUser::getOrganizationId, user.getOrganizationId());
            SysOrganizationUser orgUserOld = organizationUserService.getOne(orgUserLambdaQueryWrapper);
            // 如果不存在时，则进行修改
            if (null == orgUserOld)
            {
                // 这里查询是为了只移出所在机构的数据权限，其他数据权限保持不变
                LambdaQueryWrapper<SysOrganizationUser> organizationUserRemoveWrapper = new LambdaQueryWrapper<>();
                organizationUserRemoveWrapper.eq(SysOrganizationUser::getUserId, userEntity.getId());
                SysOrganizationUser orgUserRemove = organizationUserService.getOne(organizationUserRemoveWrapper);

                //删除旧机构的组织机构关系
                organizationUserService.remove(organizationUserRemoveWrapper);
                //保存用户和组织机构的关系
                SysOrganizationUser orgUser = new SysOrganizationUser();
                orgUser.setUserId(userEntity.getId());
                orgUser.setOrganizationId(user.getOrganizationId());
                organizationUserService.save(orgUser);
            }
        }

        List<Long> roleIds = user.getRoleIds();
        if(result && !CollectionUtils.isEmpty(roleIds))
        {
            //查询出用户原先的角色信息
            LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = UserRole.lqw();
            userRoleLambdaQueryWrapper.eq(UserRole::getUserId, userEntity.getId());
            List<UserRole> userRoleList = userRoleService.list(userRoleLambdaQueryWrapper);
            //已有数据和最新数据的差集,即原先的数据有，但最新的数据没有，得到差集是需要删除的角色
            List<UserRole> userRoleDeleteList = userRoleList.stream().filter(item -> !roleIds.contains(item.getRoleId())).collect(Collectors.toList());
            if (!StringUtils.isEmpty(userRoleDeleteList))
            {
                userRoleService.removeByIds(userRoleDeleteList.stream().map(UserRole::getId).collect(Collectors.toList()));
            }

            // 最新数据和已有数据的差集，即传进来的数据有，但是原先数据没有，得到的差集是需要新增的角色。
            List<Long> userRoleAddList = roleIds.stream().filter(item -> !userRoleList.stream().map(e -> e.getRoleId()).collect(Collectors.toList()).contains(item)).collect(Collectors.toList());
            if (!StringUtils.isEmpty(userRoleAddList))
            {
                List<UserRole> userRoleNewList = new ArrayList<>();
                //新增库里不存在的权限
                for (Long roleId : userRoleAddList)
                {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userEntity.getId());
                    userRole.setRoleId(roleId);
                    userRoleNewList.add(userRole);
                }
                userRoleService.saveBatch(userRoleNewList);
            }
        } else if(result && null != user.getRoleId())
        {
            UserRole userRole = new UserRole();
            userRole.setUserId(userEntity.getId());
            userRole.setRoleId(user.getRoleId());
            LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = UserRole.lqw();
            userRoleLambdaQueryWrapper.eq(UserRole::getUserId, userEntity.getId()).eq(UserRole::getRoleId, user.getRoleId());
            List<UserRole> urList = userRoleService.list(userRoleLambdaQueryWrapper);
            //如果这个角色不存在，则删除其他角色，保存这个角色
            if (CollectionUtils.isEmpty(urList)) {
                LambdaQueryWrapper<UserRole> wpd = UserRole.lqw();
                wpd.eq(UserRole::getUserId, userEntity.getId());
                userRoleService.remove(wpd);
                userRoleService.save(userRole);
            }
        }
        return result;
    }

    /**
     * 单个删除用户
     * @param userId
     * @return
     */
    @Override
    public boolean deleteUser(Long userId) {
        boolean result = removeById(userId);
        if (result)
        {
            this.deleteUserRelation(userId);
        }
        return result;
    }

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    @Override
    public boolean batchDeleteUser(List<Long> userIds) {
        boolean result = removeByIds(userIds);
        if (result)
        {
            for (Long userId : userIds)
            {
                this.deleteUserRelation(userId);
            }
        }
        return result;
    }

    /**
     * 通过账号查询用户
     * @param userAccount 用户账号
     * @return
     */
    @Override
    public User queryUserByAccount(String userAccount) {
        LambdaQueryWrapper<User> ew = User.lqw();
        ew.and(e -> e.eq(User::getAccount, userAccount).or().eq(User::getEmail, userAccount).or().eq(User::getMobile,
                userAccount));
        return getOne(ew);
    }

    /**
     * 用户是否存在
     * @param user
     * @return
     */
    @Override
    public Boolean checkUserExist(User user) {
        List<User> userList = userMapper.queryExistUser(user);
        // 如果存在则返回true
        if (!CollectionUtils.isEmpty(userList)) {
            return true;
        }
        return false;
    }

    /**
     * 登录时查询用户
     * @param user
     * @return
     */
    @Override
    public UserInfo queryUserInfo(User user) {

        UserInfo userInfo = userMapper.queryUserInfo(user);

        if (null == userInfo)
        {
            throw new BusinessException(ResultCodeEnum.INVALID_USERNAME.getMsg());
        }

        String roleIds = userInfo.getRoleIds();
        //组装角色ID列表，用于Oatuh2和Gateway鉴权
        if (!StringUtils.isEmpty(roleIds))
        {
            String[] roleIdsArray = roleIds.split(StrUtil.COMMA);
            userInfo.setRoleIdList(Arrays.asList(roleIdsArray));
        }

        String roleKeys = userInfo.getRoleKeys();
        //组装角色key列表，用于前端页面鉴权
        if (!StringUtils.isEmpty(roleKeys))
        {
            String[] roleKeysArray = roleKeys.split(StrUtil.COMMA);
            userInfo.setRoleKeyList(Arrays.asList(roleKeysArray));
        }

        String dataPermissionTypes = userInfo.getDataPermissionTypes();
        // 获取用户的角色数据权限级别
        if (!StringUtils.isEmpty(dataPermissionTypes))
        {
            String[] dataPermissionTypeArray = dataPermissionTypes.split(StrUtil.COMMA);
            userInfo.setDataPermissionTypeList(Arrays.asList(dataPermissionTypeArray));
        }

        String organizationIds = userInfo.getOrganizationIds();
        // 获取用户机构数据权限列表
        if (!StringUtils.isEmpty(organizationIds))
        {
            String[] organizationIdArray = organizationIds.split(StrUtil.COMMA);
            userInfo.setOrganizationIdList(Arrays.asList(organizationIdArray));
        }

        QueryUserResourceDTO queryUserResourceDTO = new QueryUserResourceDTO();
        queryUserResourceDTO.setUserId(userInfo.getId());
        List<Resource> resourceList = resourceService.queryResourceListByUserId(queryUserResourceDTO);

        // 查询用户权限列表key，用于前端页面鉴权
        List<String> menuList = resourceList.stream().map(Resource::getResourceKey).collect(Collectors.toList());
        userInfo.setResourceKeyList(menuList);

        // 查询用户资源列表，用于SpringSecurity鉴权
        List<String> resourceUrlList = resourceList.stream().filter(s-> !ResourceEnum.MODULE.getCode().equals(s.getResourceType()) && !ResourceEnum.MENU.getCode().equals(s.getResourceType())).map(Resource::getResourceUrl).collect(Collectors.toList());
        userInfo.setResourceUrlList(resourceUrlList);

        // 查询用户菜单树，用于页面展示
        List<Resource> menuTree = resourceService.queryMenuTreeByUserId(userInfo.getId());
        userInfo.setMenuTree(menuTree);

        return userInfo;
    }

    /**
     * 删除用户的关联关系
     * @param userId
     */
    private void deleteUserRelation(Long userId) {
        if (null != userId)
        {
            //删除角色关联
            LambdaQueryWrapper<UserRole> wpd = UserRole.lqw();
            wpd.eq(UserRole::getUserId, userId);
            userRoleService.remove(wpd);
            //删除组织关联
            LambdaQueryWrapper<SysOrganizationUser> organizationUserLambdaQueryWrapper = SysOrganizationUser.lqw();
            organizationUserLambdaQueryWrapper.eq(SysOrganizationUser::getUserId, userId);
            organizationUserService.remove(organizationUserLambdaQueryWrapper);
        }
    }

    /**
     * 处理省市区数据
     * @param userEntity
     * @param areas
     * @return
     */
    private User resolveAreas(User userEntity, List<String> areas){
        if (!CollectionUtils.isEmpty(areas)) {
            userEntity.setProvince(areas.get(LzyCloudConstant.Address.PROVINCE));
            userEntity.setCity(areas.get(LzyCloudConstant.Address.CITY));
            userEntity.setArea(areas.get(LzyCloudConstant.Address.AREA));
        }
        return userEntity;
    }

}
