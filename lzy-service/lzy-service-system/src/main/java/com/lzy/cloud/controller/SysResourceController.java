package com.lzy.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.cloud.dto.CreateResourceDTO;
import com.lzy.cloud.dto.UpdateResourceDTO;
import com.lzy.cloud.entity.Resource;
import com.lzy.cloud.service.IResourceService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysResourceController
 * @Description Resource前端控制器
 * @Author LiZuoYang
 * @Date 2023/2/2 14:22
 **/
@RestController
@RequestMapping(value = "resource")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "SysResourceController|资源相关的前端控制器", tags = "资源接口")
@RefreshScope
public class SysResourceController {
    private final IResourceService resourceService;

    /**
     * 查询权限资源树
     *
     * @param parentId
     * @return
     */
    @GetMapping(value = "/tree")
    @ApiOperation(value = "查询权限资源树", notes = "树状展示权限资源信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Long")
    public Result<List<Resource>> queryResourceTree(Long parentId) {
        List<Resource> treeList = resourceService.queryResourceByParentId(parentId);
        return Result.data(treeList);
    }

    /**
     * 修改资源
     *
     * @param resourceId
     * @return
     */
    @PostMapping("/delete/{resourceId}")
    @ApiOperation(value = "删除权限资源")
    @ApiImplicitParam(paramType = "path", name = "resourceId", value = "权限资源ID", required = true, dataType = "Long")
    public Result<?> deleteResource(@PathVariable("resourceId") Long resourceId) {
        boolean result = resourceService.deleteResource(resourceId);
        if (result) {
            return Result.success();
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 修改资源权限状态
     */
    @PostMapping("/status/{resourceId}/{resourceStatus}")
    @ApiOperation(value = "修改权限状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceId", value = "权限ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "resourceStatus", value = "权限状态", required = true, dataType = "Integer",
                    paramType = "path")})
    public Result<?> updateStatus(@PathVariable("resourceId") Long resourceId,
                                  @PathVariable("resourceStatus") Integer resourceStatus) {
        if (null == resourceId || StringUtils.isEmpty(resourceStatus)) {
            return Result.error("ID和状态不能为空");
        }
        Resource resource = new Resource();
        resource.setId(resourceId);
        resource.setResourceStatus(resourceStatus);
        boolean result = resourceService.updateResource(resource);
        if (result) {
            return Result.success();
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 修改资源
     *
     * @param resource
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新权限资源")
    public Result<Resource> updateResource(@RequestBody UpdateResourceDTO resource) {
        Resource resEntity = BeanCopierUtils.copyByClass(resource, Resource.class);
        boolean result = resourceService.updateResource(resEntity);
        if (result) {
            return Result.data(resEntity);
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }

    /**
     * 检查资源的key是否存在
     *
     * @param resource
     * @return
     */
    @PostMapping(value = "/check")
    @ApiOperation(value = "校验Resource是否存在", notes = "校验Resource是否存在")
    public Result<Boolean> checkResource(CheckExistDTO resource) {
        String field = resource.getCheckField();
        String value = resource.getCheckValue();
        QueryWrapper<Resource> resourceQueryWrapper = Resource.qw();
        resourceQueryWrapper.eq(field, value);
        if(null != resource && null != resource.getId()) {
            resourceQueryWrapper.ne("id", resource.getId());
        }
        long count = resourceService.count(resourceQueryWrapper);
        if (LzyCloudConstant.COUNT_ZERO == count){
            return Result.data(true);
        } else{
            return Result.data(false);
        }
    }

    /**
     * 添加资源
     *
     * @param resource
     * @return
     */
    @PostMapping("/create")
    @ApiOperation(value = "添加权限资源")
    public Result<Resource> createResource(@RequestBody CreateResourceDTO resource) {
        Resource resEntity = BeanCopierUtils.copyByClass(resource, Resource.class);
        boolean result = resourceService.createResource(resEntity);
        if (result) {
            return Result.data(resEntity);
        } else {
            return Result.error(ResultCodeEnum.FAILED);
        }
    }
}
