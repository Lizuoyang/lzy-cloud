package com.lzy.cloud.feign;

import com.lzy.cloud.dto.QueryUserDTO;
import com.lzy.cloud.entity.User;
import com.lzy.cloud.entity.UserInfo;
import com.lzy.cloud.service.IUserService;
import com.lzy.platform.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 用户微服务调用接口
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
@RestController
@RequestMapping(value = "/feign/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "UserFeign|提供微服务调用接口")
@RefreshScope
public class UserFeign {

    private final IUserService userService;

    @GetMapping(value = "/test/by/id")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public Result<User> testById(Long id) {
        User user = userService.getById(id);
        return Result.data(user);
    }

    @GetMapping(value = "/query/by/id")
    @ApiOperation(value = "通过用户id查询用户信息", notes = "通过用户id查询用户信息")
    public Result<UserInfo> queryById(Long id) {
        User user = new User();
        user.setId(id);
        UserInfo userInfo = userService.queryUserInfo(user);
        return Result.data(userInfo);
    }

    @GetMapping(value = "/query/by/account")
    @ApiOperation(value = "通过账号查询用户信息", notes = "通过账号查询用户信息")
    public Result<UserInfo> queryByAccount(String account) {
        User user = new User();
        user.setAccount(account);
        UserInfo userInfo = userService.queryUserInfo(user);
        return Result.data(userInfo);
    }

    @PostMapping("/list")
    @ApiOperation(value =  "查询用户列表", notes = "用于导出excel查询数据")
    public Result listUsers(@ApiIgnore @RequestBody QueryUserDTO queryUserDTO) {
        List<UserInfo> userInfos = userService.selectUserList(queryUserDTO);
        return Result.data(userInfos);
    }
}
