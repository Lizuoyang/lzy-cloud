package com.lzy.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.cloud.dto.CreateOrganizationDTO;
import com.lzy.cloud.dto.QueryOrganizationDTO;
import com.lzy.cloud.dto.UpdateOrganizationDTO;
import com.lzy.cloud.entity.SysOrganization;
import com.lzy.cloud.service.ISysOrganizationService;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.base.dto.CheckExistDTO;
import com.lzy.platform.base.enums.ResultCodeEnum;
import com.lzy.platform.base.result.Result;
import com.lzy.platform.base.utils.BeanCopierUtils;
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
 * 组织表 前端控制器
 * </p>
 *
 * @author lzy
 * @since 2023-05-08
 */
@RestController
@RequestMapping(value = "organization")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "OrganizationController|组织机构相关的前端控制器", tags = {"组织机构配置"})
@RefreshScope
public class OrganizationController {

    private final ISysOrganizationService organizationService;

    /**
     * 查询组织树
     *
     * @param organizationDTO
     * @return
     */
    @GetMapping(value = "/tree")
    @ApiOperation(value = "查询组织机构树", notes = "树状展示组织机构信息")
    public Result<List<SysOrganization>> queryOrganizationTree(QueryOrganizationDTO organizationDTO) {
        SysOrganization organization = BeanCopierUtils.copyByClass(organizationDTO, SysOrganization.class);
        List<SysOrganization> treeList = organizationService.queryOrganizationByParentId(organization);
        return Result.data(treeList);
    }

    /**
     * 级联查询
     *
     * @param organizationDTO
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "查询组织机构树", notes = "树状展示组织机构信息")
    public Result<List<SysOrganization>> queryOrganizationList(QueryOrganizationDTO organizationDTO) {
        SysOrganization organization = BeanCopierUtils.copyByClass(organizationDTO, SysOrganization.class);
        List<SysOrganization> treeList = organizationService.queryOrganizationList(organization);
        return Result.data(treeList);
    }

    /**
     * 添加组织
     */
    @PostMapping("/create")
    @ApiOperation(value = "添加组织机构")
    public Result<SysOrganization> create(@RequestBody CreateOrganizationDTO org) {
        SysOrganization orgEntity = BeanCopierUtils.copyByClass(org, SysOrganization.class);
        if (null != org && !CollectionUtils.isEmpty(org.getAreas())) {
            orgEntity.setProvince(org.getAreas().get(LzyCloudConstant.Address.PROVINCE));
            orgEntity.setCity(org.getAreas().get(LzyCloudConstant.Address.CITY));
            orgEntity.setArea(org.getAreas().get(LzyCloudConstant.Address.AREA));
        }
        boolean result = organizationService.createOrganization(orgEntity);
        if (result) {
            return Result.data(orgEntity);
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 修改组织
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新组织机构")
    public Result<SysOrganization> update(@RequestBody UpdateOrganizationDTO org) {
        SysOrganization orgEntity = BeanCopierUtils.copyByClass(org, SysOrganization.class);
        if (null != org && !CollectionUtils.isEmpty(org.getAreas())) {
            orgEntity.setProvince(org.getAreas().get(LzyCloudConstant.Address.PROVINCE));
            orgEntity.setCity(org.getAreas().get(LzyCloudConstant.Address.CITY));
            orgEntity.setArea(org.getAreas().get(LzyCloudConstant.Address.AREA));
        }
        boolean result = organizationService.updateOrganization(orgEntity);
        if (result) {
            return Result.data(orgEntity);
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 修改修改组织机构状态
     */
    @PostMapping("/status/{organizationId}/{organizationStatus}")
    @ApiOperation(value = "修改角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationId", value = "组织机构ID", required = true, dataTypeClass = Long.class,
                    paramType = "path"),
            @ApiImplicitParam(name = "organizationStatus", value = "组织机构状态", required = true, dataTypeClass = Integer.class,
                    paramType = "path")})
    public Result<?> updateStatus(@PathVariable("organizationId") Long organizationId,
                                  @PathVariable("organizationStatus") Integer organizationStatus) {
        if (null == organizationId || StringUtils.isEmpty(organizationStatus)) {
            return Result.error("ID和状态不能为空");
        }
        SysOrganization organization = new SysOrganization();
        organization.setId(organizationId);
        organization.setOrganizationStatus(organizationStatus);
        boolean result = organizationService.updateOrganization(organization);
        if (result) {
            return Result.success();
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 删除组织
     */
    @PostMapping("/delete/{organizationId}")
    @ApiOperation(value = "删除组织机构")
    @ApiImplicitParam(paramType = "path", name = "organizationId", value = "组织机构ID", required = true, dataTypeClass = Long.class)
    public Result<?> delete(@PathVariable("organizationId") Long organizationId) {
        boolean result = organizationService.deleteOrganization(organizationId);
        if (result) {
            return Result.success();
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    @PostMapping(value = "/check")
    @ApiOperation(value = "校验组织是否存在", notes = "校验组织是否存在")
    public Result<Boolean> checkOrganization(@RequestBody CheckExistDTO organization) {
        String field = organization.getCheckField();
        String value = organization.getCheckValue();
        QueryWrapper<SysOrganization> organizationQueryWrapper = new QueryWrapper<>();
        organizationQueryWrapper.eq(field, value);
        if (null != organization.getId()) {
            organizationQueryWrapper.ne("id", organization.getId());
        }
        long count = organizationService.count(organizationQueryWrapper);
        if (LzyCloudConstant.COUNT_ZERO == count) {
            return Result.data(true);
        } else {
            return Result.data(false);
        }
    }
}
