package com.lzy.cloud.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dto.*;
import com.lzy.cloud.entity.SysDataPermissionRule;
import com.lzy.cloud.entity.SysRoleDataPermission;
import com.lzy.cloud.service.ISysDataPermissionRuleService;
import com.lzy.cloud.service.ISysRoleDataPermissionService;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.base.dto.CheckExistDTO;
import com.lzy.platform.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* <p>
* 数据权限配置表 前端控制器
* </p>
*
* @author lzy
* @since 2023-05-13
*/
@RestController
@RequestMapping("/data/permission/rule")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "DataPermissionRoleController|数据权限配置表前端控制器", tags = {"数据权限配置"})
@RefreshScope
public class DataPermissionController {

    private final ISysDataPermissionRuleService dataPermissionRuleService;

    private final ISysRoleDataPermissionService roleDataPermissionService;

    /**
    * 查询数据权限配置表列表
    */
    @GetMapping("/list")
    @ApiOperation(value = "查询数据权限配置表列表")
    public Result<Page<DataPermissionRuleDTO>> list(QueryDataPermissionRuleDTO queryDataPermissionRoleDTO, Page<DataPermissionRuleDTO> page) {
        Page<DataPermissionRuleDTO> pageDataPermissionRole = dataPermissionRuleService.queryDataPermissionRoleList(page, queryDataPermissionRoleDTO);
        return Result.data(pageDataPermissionRole);
    }

    /**
    * 查询数据权限配置表详情
    */
    @GetMapping("/query")
    @ApiOperation(value = "查询数据权限配置表详情")
    public Result<?> query(QueryDataPermissionRuleDTO queryDataPermissionRoleDTO) {
        DataPermissionRuleDTO dataPermissionRoleDTO = dataPermissionRuleService.queryDataPermissionRole(queryDataPermissionRoleDTO);
        return Result.data(dataPermissionRoleDTO);
    }

    /**
    * 添加数据权限配置表
    */
    @PostMapping("/create")
    @ApiOperation(value = "添加数据权限配置表")
    public Result<?> create(@RequestBody CreateDataPermissionRuleDTO dataPermissionRule) {
        boolean result = dataPermissionRuleService.createDataPermissionRole(dataPermissionRule);
        return Result.result(result);
    }

    /**
    * 修改数据权限配置表
    */
    @PostMapping("/update")
    @ApiOperation(value = "更新数据权限配置表")
    public Result<?> update(@RequestBody UpdateDataPermissionRuleDTO dataPermissionRule) {
        boolean result = dataPermissionRuleService.updateDataPermissionRole(dataPermissionRule);
        return Result.result(result);
    }

    /**
    * 删除数据权限配置表
    */
    @PostMapping("/delete/{dataPermissionRuleId}")
    @ApiOperation(value = "删除数据权限配置表")
    @ApiImplicitParam(paramType = "path", name = "dataPermissionRuleId", value = "数据权限配置表ID", required = true, dataTypeClass = Long.class)
    public Result<?> delete(@PathVariable("dataPermissionRuleId") Long dataPermissionRuleId) {
        if (null == dataPermissionRuleId) {
            return Result.error("ID不能为空");
        }
        boolean result = dataPermissionRuleService.deleteDataPermissionRole(dataPermissionRuleId);
        return Result.result(result);
    }

    /**
    * 批量删除数据权限配置表
    */
    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除数据权限配置表")
    @ApiImplicitParam(name = "dataPermissionRuleIds", value = "数据权限配置表ID列表", required = true, dataTypeClass = List.class)
    public Result<?> batchDelete(@RequestBody List<Long> dataPermissionRuleIds) {
        if (CollectionUtils.isEmpty(dataPermissionRuleIds)) {
            return Result.error("数据权限配置表ID列表不能为空");
        }
        boolean result = dataPermissionRuleService.batchDeleteDataPermissionRole(dataPermissionRuleIds);
        return Result.result(result);
    }

    /**
    * 修改数据权限配置表状态
    */
    @PostMapping("/status/{dpId}/{dpStatus}")
    @ApiOperation(value = "修改数据权限配置表状态")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "dpId", value = "数据权限配置表ID", required = true, dataTypeClass = Long.class, paramType = "path"),
    @ApiImplicitParam(name = "dpStatus", value = "数据权限配置表状态", required = true, dataTypeClass = Integer.class, paramType = "path") })
    public Result<?> updateStatus(@PathVariable("dpId") Long dpId,
            @PathVariable("dpStatus") Integer dpStatus) {
        if (null == dpId || StringUtils.isEmpty(dpStatus)) {
            return Result.error("ID和状态不能为空");
        }
        UpdateDataPermissionRuleDTO dataPermissionRule = new UpdateDataPermissionRuleDTO();
        dataPermissionRule.setId(dpId);
        dataPermissionRule.setStatus(dpStatus);
        boolean result = dataPermissionRuleService.updateDataPermissionRole(dataPermissionRule);
        return Result.result(result);
    }

    /**
    * 校验数据权限配置表是否存在
    *
    * @param dataPermissionRule
    * @return
    */
    @PostMapping(value = "/check")
    @ApiOperation(value = "校验数据权限配置表是否存在", notes = "校验数据权限配置表是否存在")
    public Result<Boolean> checkDataPermissionRoleExist(@RequestBody CheckExistDTO dataPermissionRule) {
        String field = dataPermissionRule.getCheckField();
        String value = dataPermissionRule.getCheckValue();
        QueryWrapper<SysDataPermissionRule> dataPermissionRoleQueryWrapper = new QueryWrapper<>();
        dataPermissionRoleQueryWrapper.eq(field, value);
        if(null != dataPermissionRule.getId()) {
            dataPermissionRoleQueryWrapper.ne("id", dataPermissionRule.getId());
        }
        long count = dataPermissionRuleService.count(dataPermissionRoleQueryWrapper);
        if (LzyCloudConstant.COUNT_ZERO == count){
            return Result.data(true);
        } else{
            return Result.data(false);
        }
    }

    /**
     * 获取拥有某个数据权限的所有角色列表
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get/roles/{id}")
    @ApiOperation(value = "获取拥有某个数据权限的所有角色列表")
    @ApiImplicitParam(paramType = "path", name = "id", value = "数据权限ID", required = true, dataTypeClass = Long.class)
    public Result<List<SysRoleDataPermission>> queryPermissionRoles(@PathVariable("id") Long id) {
        LambdaQueryWrapper<SysRoleDataPermission> ew = new LambdaQueryWrapper<>();
        ew.eq(SysRoleDataPermission::getDataPermissionId, id);
        List<SysRoleDataPermission> list = roleDataPermissionService.list(ew);
        return Result.data(list);
    }

    /**
     * 批量修改数据权限的角色列表
     *
     * @param updateRoleDataPermissionDTO
     * @return
     */
    @PostMapping(value = "/batch/role/update")
    @ApiOperation(value = "修改角色的权限资源")
    public Result<?> updateRoleResource(@RequestBody UpdateRoleDataPermissionDTO updateRoleDataPermissionDTO) {
        boolean result = roleDataPermissionService.updateDataPermissionRoleList(updateRoleDataPermissionDTO);
        return Result.result(result);
    }
 }
