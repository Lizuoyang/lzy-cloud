package com.lzy.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dto.CreateRoleDTO;
import com.lzy.cloud.dto.QueryRoleDTO;
import com.lzy.cloud.dto.UpdateRoleDTO;
import com.lzy.cloud.dto.UpdateRoleResourceDTO;
import com.lzy.cloud.entity.Role;
import com.lzy.cloud.entity.RoleResource;
import com.lzy.cloud.service.IRoleResourceService;
import com.lzy.cloud.service.IRoleService;
import com.lzy.platform.base.result.PageResult;
import com.lzy.platform.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleController
 * @Description Role前端控制器
 * @Author LiZuoYang
 * @Date 2022/6/21 11:41
 **/
@RestController
@RequestMapping(value = "role")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "SysRoleController|角色相关的前端控制器", tags = "角色接口")
@RefreshScope
public class SysRoleController {
    private final IRoleService roleService;

    private final IRoleResourceService roleResourceService;

    /**
     * 查询角色列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询角色列表")
    public PageResult<Role> list(QueryRoleDTO queryRoleDTO, Page<Role> page) {
        Page<Role> pageRole = roleService.selectRoleList(page, queryRoleDTO);
        PageResult<Role> pageResult = new PageResult<>(pageRole.getTotal(), pageRole.getRecords());
        return pageResult;
    }

    /**
     * 查询所有角色列表
     *
     * @return
     */
    @GetMapping(value = "/all")
    @ApiOperation(value = "查询所有角色列表")
    public Result<List<Role>> queryAll() {
        List<Role> result = roleService.list().stream().filter(r -> r.getRoleStatus() == 1).collect(Collectors.toList());
        return Result.data(result);
    }

    /**
     * 添加角色
     */
    @PostMapping("/create")
    @ApiOperation(value = "添加角色")
    public Result<?> create(@RequestBody CreateRoleDTO role) {
        boolean result = roleService.createRole(role);
        return Result.result(result);
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新角色")
    public Result<?> update(@RequestBody UpdateRoleDTO role) {
        boolean result = roleService.updateRole(role);
        return Result.result(result);
    }

    /**
     * 删除角色
     */
    @PostMapping("/delete/{roleId}")
    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "Long")
    public Result<?> delete(@PathVariable("roleId") Long roleId) {
        if (null == roleId) {
            return Result.error("ID不能为空");
        }
        boolean result = roleService.deleteRole(roleId);
        return Result.result(result);
    }

    /**
     * 修改角色状态
     */
    @PostMapping("/status/{roleId}/{roleStatus}")
    @ApiOperation(value = "修改角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "roleStatus", value = "角色状态", required = true, dataType = "Integer",
                    paramType = "path")})
    public Result<?> updateStatus(@PathVariable("roleId") Long roleId,
                                  @PathVariable("roleStatus") Integer roleStatus) {
        if (null == roleId || StringUtils.isEmpty(roleStatus)) {
            return Result.error("ID和状态不能为空");
        }
        LambdaUpdateWrapper<Role> userLambdaUpdateWrapper = Role.luw();
        userLambdaUpdateWrapper.eq(Role::getId, roleId);
        userLambdaUpdateWrapper.set(Role::getRoleStatus, roleStatus);
        boolean result = roleService.update(userLambdaUpdateWrapper);
        return Result.result(result);
    }

    /**
     * 获取角色资源
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/resource/{roleId}")
    @ApiOperation(value = "获取角色的权限资源")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "Integer")
    public Result<List<RoleResource>> queryRoleResource(@PathVariable("roleId") Integer roleId) {
        LambdaQueryWrapper<RoleResource> ew = RoleResource.lqw();
        ew.eq(RoleResource::getRoleId, roleId);
        List<RoleResource> list = roleResourceService.list(ew);
        return Result.data(list);
    }

    /**
     * 修改角色资源
     *
     * @param updateRoleResource
     * @return
     */
    @PostMapping(value = "/resource/update")
    @ApiOperation(value = "修改角色的权限资源")
    public Result<?> updateRoleResource(@RequestBody UpdateRoleResourceDTO updateRoleResource) {
        boolean result = roleResourceService.updateList(updateRoleResource);
        return Result.result(result);
    }
}
