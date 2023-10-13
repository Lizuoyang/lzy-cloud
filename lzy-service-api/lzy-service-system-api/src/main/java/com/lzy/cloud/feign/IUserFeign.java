package com.lzy.cloud.feign;

import com.lzy.cloud.dto.QueryUserDTO;
import com.lzy.platform.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 用户接口（Feign调用）
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
@FeignClient(name = "lzy-service-system", contextId = "UserClient")
public interface IUserFeign {
    /**
     * 测试接口
     *
     * @param id id
     * @return {@link Result}<{@link Object}>
     */
    @GetMapping(value = "/feign/user/test/by/id")
    Result<Object> testById(@RequestParam("id") Long id);

    /**
     * 通过用户id查询用户信息
     * @param id
     * @return
     */
    @GetMapping(value = "/feign/user/query/by/id")
    Result<Object> queryById(@RequestParam("id") Long id);

    /**
     * 通过账号查询用户
     *
     * @param account
     * @return
     */
    @GetMapping("/feign/user/query/by/account")
    Result<Object> queryUserByAccount(@RequestParam("account") String account);

    /**
     * 查询用户列表（无分页）
     * @param queryUserDTO
     * @return
     */
    @PostMapping("/feign/user/list")
    Result<Object> listUsers(@RequestBody QueryUserDTO queryUserDTO);
}
