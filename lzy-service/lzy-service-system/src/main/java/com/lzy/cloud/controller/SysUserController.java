package com.lzy.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dto.CreateUserDTO;
import com.lzy.cloud.dto.QueryUserDTO;
import com.lzy.cloud.dto.UpdateDataPermissionUserDTO;
import com.lzy.cloud.dto.UpdateUserDTO;
import com.lzy.cloud.entity.User;
import com.lzy.cloud.entity.UserInfo;
import com.lzy.cloud.service.ISysDataPermissionUserService;
import com.lzy.cloud.service.IUserService;
import com.lzy.platform.base.annotation.auth.CurrentUser;
import com.lzy.platform.base.constant.AuthConstant;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.base.domain.LzyCloudUser;
import com.lzy.platform.base.dto.CheckExistDTO;
import com.lzy.platform.base.enums.ResultCodeEnum;
import com.lzy.platform.base.result.PageResult;
import com.lzy.platform.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


/**
 * @ClassName SysUserController
 * @Description User前端控制器
 * @Author LiZuoYang
 * @Date 2022/6/21 11:41
 **/
@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "UserController|用户相关的前端控制器", tags = "用户接口")
@RefreshScope
public class SysUserController {
    private final IUserService userService;
    private final ISysDataPermissionUserService dataPermissionUserService;
    @ApiOperation(value = "测试接口")
    @GetMapping("/index")
    public Result test() {
        return Result.data("test");
    }

    @ApiOperation(value = "查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "用户名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "用户状态", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "current", value = "当前页", required = false, dataType = "Integer", paramType = "query") })
    @GetMapping("/list")
    public PageResult listUsers(@ApiIgnore QueryUserDTO queryUserDTO, @ApiIgnore Page<UserInfo> page) {
        Page<UserInfo> pageUser = userService.selectUserList(page, queryUserDTO);
        PageResult<UserInfo> pageResult = new PageResult<>(pageUser.getTotal(), pageUser.getRecords());
        return pageResult;
    }

    /**
     * 添加用户
     */
    @PostMapping("/create")
    @ApiOperation(value = "添加用户")
    public Result<?> create(@RequestBody @Valid CreateUserDTO user) {
        CreateUserDTO userDTO = userService.createUser(user);
        return Result.data(userDTO.getId());
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新用户信息")
    public Result<?> update(@RequestBody UpdateUserDTO user) {
        boolean result = userService.updateUser(user);
        if (result) {
            return Result.success();
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete/{userId}")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(paramType = "path", name = "userId", value = "用户ID", required = true, dataType = "Long")
    public Result<?> delete(@PathVariable("userId") Long userId) {
        if (null == userId) {
            return Result.error("用户ID不能为空");
        }
        boolean result = userService.deleteUser(userId);
        if (result) {
            return Result.success();
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 修改用户状态
     */
    @PostMapping("/status/{userId}/{status}")
    @ApiOperation(value = "管理员修改用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "status", value = "用户状态", required = true, dataType = "Integer", paramType = "path")})
    public Result<?> updateStatus(@PathVariable("userId") Long userId, @PathVariable("status") Integer status) {
        if (null == userId || StringUtils.isEmpty(status)) {
            return Result.error("ID和状态不能为空");
        }
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = User.luw();
        userLambdaUpdateWrapper.eq(User::getId, userId);
        userLambdaUpdateWrapper.set(User::getStatus, status);
        boolean result = userService.update(userLambdaUpdateWrapper);
        if (result) {
            return Result.success();
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 更新用户数据权限
     */
    @PostMapping("/update/organization/data/permission")
    @ApiOperation(value = "更新用户数据权限")
    public Result<?> updateUserDataPermission(@Valid @RequestBody UpdateDataPermissionUserDTO updateDataPermission) {
        boolean result = dataPermissionUserService.updateUserOrganizationDataPermission(updateDataPermission);
        return Result.result(result);
    }

    /**
     * 校验用户账号是否存在
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/check")
    @ApiOperation(value = "校验用户账号是否存在", notes = "校验用户账号是否存在")
    public Result<Boolean> checkUserExist(@RequestBody CheckExistDTO user) {
        String field = user.getCheckField();
        String value = user.getCheckValue();
        QueryWrapper<User> userQueryWrapper = User.qw();
        userQueryWrapper.eq(field, value);
        if(null != user && null != user.getId()) {
            userQueryWrapper.ne("id", user.getId());
        }
        long count = userService.count(userQueryWrapper);
        return Result.data(LzyCloudConstant.COUNT_ZERO == count);
    }


    /**
     * 检查密码
     *
     * @param account     账户
     * @param newPassword 新密码
     * @return {@link Result}<{@link Boolean}>
     */
    @GetMapping(value = "/check/password")
    @ApiOperation(value = "校验当前密码是否正确", notes = "校验当前密码是否正确")
    public Result<Boolean> checkPassword(@ApiIgnore @CurrentUser LzyCloudUser currentUser,@RequestParam("account") String account, @RequestParam("newPassword") String newPassword) {
        // 获取当前用户
        String currentUserPassword = currentUser.getPassword();
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        boolean matches = passwordEncoder.matches(AuthConstant.BCRYPT + account + DigestUtils.md5DigestAsHex(newPassword.getBytes()), currentUserPassword);
        return Result.data(matches);
    }
}
