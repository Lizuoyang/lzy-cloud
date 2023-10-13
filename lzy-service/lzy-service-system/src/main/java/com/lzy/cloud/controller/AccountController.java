package com.lzy.cloud.controller;

import com.lzy.cloud.entity.User;
import com.lzy.cloud.entity.UserInfo;
import com.lzy.cloud.service.IUserService;
import com.lzy.platform.base.annotation.auth.CurrentUser;
import com.lzy.platform.base.domain.LzyCloudUser;
import com.lzy.platform.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @ClassName AccountController
 * @Description 账号相关的前端控制器
 * @Author LiZuoYang
 * @Date 2022/8/3 14:49
 **/
@RestController
@RequestMapping(value = "account")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "AccountController|账号相关的前端控制器", tags = "账号接口")
@Slf4j
@RefreshScope
public class AccountController {
    private final IUserService userService;

    /**
     * 获取登录后的用户信息
     */
    @GetMapping("/user/info")
    @ApiOperation(value = "登录后获取用户个人信息")
    public Result<UserInfo> userInfo( @ApiIgnore @CurrentUser LzyCloudUser currentUser) {
        User user = new User();
        user.setId(currentUser.getId());
        UserInfo userInfo = userService.queryUserInfo(user);
        return Result.data(userInfo);
    }
}
