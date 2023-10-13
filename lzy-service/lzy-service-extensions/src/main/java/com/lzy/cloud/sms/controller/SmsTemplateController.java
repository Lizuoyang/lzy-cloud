package com.lzy.cloud.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.sms.dto.CreateSmsTemplateDTO;
import com.lzy.cloud.sms.dto.QuerySmsTemplateDTO;
import com.lzy.cloud.sms.dto.SmsTemplateDTO;
import com.lzy.cloud.sms.dto.UpdateSmsTemplateDTO;
import com.lzy.cloud.sms.entity.SmsTemplate;
import com.lzy.cloud.sms.service.ISmsTemplateService;
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
 * 短信模板配置表 前端控制器
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
@RestController
@RequestMapping("/extension/sms/template")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "SmsTemplateController|短信模板配置表前端控制器", tags = {"短信模板配置"})
@RefreshScope
public class SmsTemplateController {

    private final ISmsTemplateService smsTemplateService;

    /**
    * 查询短信模板配置表列表
    *
    * @param querySmsTemplateDTO
    * @param page
    * @return
    */
    @GetMapping("/list")
    @ApiOperation(value = "查询短信模板配置表列表")
    public Result<Page<SmsTemplateDTO>> list(QuerySmsTemplateDTO querySmsTemplateDTO, Page<SmsTemplateDTO> page) {
        Page<SmsTemplateDTO> pageSmsTemplate = smsTemplateService.querySmsTemplateList(page, querySmsTemplateDTO);
        return Result.data(pageSmsTemplate);
    }

    /**
     * 查询短信模板配置表列表
     *
     * @param querySmsTemplateDTO
     * @return
     */
    @GetMapping("/list/all")
    @ApiOperation(value = "查询全部短信模板配置表列表")
    public Result<List<SmsTemplateDTO>> list(QuerySmsTemplateDTO querySmsTemplateDTO) {
        List<SmsTemplateDTO> list = smsTemplateService.querySmsTemplateList(querySmsTemplateDTO);
        return Result.data(list);
    }

    /**
    * 查询短信模板配置表详情
    *
    * @param querySmsTemplateDTO
    * @return
    */
    @GetMapping("/query")
    @ApiOperation(value = "查询短信模板配置表详情")
    public Result<?> query(QuerySmsTemplateDTO querySmsTemplateDTO) {
        SmsTemplateDTO smsTemplateDTO = smsTemplateService.querySmsTemplate(querySmsTemplateDTO);
        return Result.data(smsTemplateDTO);
    }

    /**
    * 添加短信模板配置表
    *
    * @param smsTemplate
    * @return
    */
    @PostMapping("/create")
    @ApiOperation(value = "添加短信模板配置表")
    public Result<?> create(@RequestBody CreateSmsTemplateDTO smsTemplate) {
        boolean result = smsTemplateService.createSmsTemplate(smsTemplate);
        return Result.result(result);
    }

    /**
    * 修改短信模板配置表
    *
    * @param smsTemplate
    * @return
    */
    @PostMapping("/update")
    @ApiOperation(value = "更新短信模板配置表")
    public Result<?> update(@RequestBody UpdateSmsTemplateDTO smsTemplate) {
        boolean result = smsTemplateService.updateSmsTemplate(smsTemplate);
        return Result.result(result);
    }

    /**
    * 删除短信模板配置表
    *
    * @param smsTemplateId
    * @return
    */
    @PostMapping("/delete/{smsTemplateId}")
    @ApiOperation(value = "删除短信模板配置表")
    @ApiImplicitParam(paramType = "path", name = "smsTemplateId", value = "短信模板配置表ID", required = true, dataTypeClass = Long.class)
    public Result<?> delete(@PathVariable("smsTemplateId") Long smsTemplateId) {
        if (null == smsTemplateId) {
            return Result.error("ID不能为空");
        }
        boolean result = smsTemplateService.deleteSmsTemplate(smsTemplateId);
        return Result.result(result);
    }

    /**
    * 批量删除短信模板配置表
    *
    * @param smsTemplateIds
    * @return
    */
    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除短信模板配置表")
    @ApiImplicitParam(name = "smsTemplateIds", value = "短信模板配置表ID列表", required = true, dataTypeClass = List.class)
    public Result<?> batchDelete(@RequestBody List<Long> smsTemplateIds) {
        if (CollectionUtils.isEmpty(smsTemplateIds)) {
            return Result.error("短信模板配置表ID列表不能为空");
        }
        boolean result = smsTemplateService.batchDeleteSmsTemplate(smsTemplateIds);
        return Result.result(result);
    }
     /**
     * 修改短信模板配置表状态
     *
     * @param smsTemplateId
     * @param templateStatus
     * @return
     */
     @PostMapping("/status/{smsTemplateId}/{templateStatus}")
     @ApiOperation(value = "修改短信模板配置表状态")
     @ApiImplicitParams({
     @ApiImplicitParam(name = "smsTemplateId", value = "短信模板配置表ID", required = true, dataTypeClass = Long.class, paramType = "path"),
     @ApiImplicitParam(name = "templateStatus", value = "短信模板配置表状态", required = true, dataTypeClass = Integer.class, paramType = "path") })
     public Result<?> updateStatus(@PathVariable("smsTemplateId") Long smsTemplateId,
         @PathVariable("templateStatus") Integer templateStatus) {

         if (null == smsTemplateId || StringUtils.isEmpty(templateStatus)) {
           return Result.error("ID和状态不能为空");
         }
         UpdateSmsTemplateDTO smsTemplate = new UpdateSmsTemplateDTO();
         smsTemplate.setId(smsTemplateId);
         smsTemplate.setTemplateStatus(templateStatus);
         boolean result = smsTemplateService.updateSmsTemplate(smsTemplate);
         return Result.result(result);
     }

    /**
    * 校验短信模板配置表是否存在
    *
    * @param smsTemplate
    * @return
    */
    @PostMapping(value = "/check")
    @ApiOperation(value = "校验短信模板配置表是否存在", notes = "校验短信模板配置表是否存在")
    public Result<Boolean> checkSmsTemplateExist(@RequestBody CheckExistDTO smsTemplate) {
        String field = smsTemplate.getCheckField();
        String value = smsTemplate.getCheckValue();
        QueryWrapper<SmsTemplate> smsTemplateQueryWrapper = SmsTemplate.qw();
        smsTemplateQueryWrapper.eq(field, value);
        if(null != smsTemplate.getId()) {
            smsTemplateQueryWrapper.ne("id", smsTemplate.getId());
        }
        long count = smsTemplateService.count(smsTemplateQueryWrapper);
        return Result.data(LzyCloudConstant.COUNT_ZERO == count);
    }

 }
