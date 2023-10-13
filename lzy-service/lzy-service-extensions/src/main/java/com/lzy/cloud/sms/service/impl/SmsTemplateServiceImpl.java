package com.lzy.cloud.sms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.lzy.cloud.sms.dto.CreateSmsTemplateDTO;
import com.lzy.cloud.sms.dto.QuerySmsTemplateDTO;
import com.lzy.cloud.sms.dto.SmsTemplateDTO;
import com.lzy.cloud.sms.dto.UpdateSmsTemplateDTO;
import com.lzy.cloud.sms.entity.SmsChannel;
import com.lzy.cloud.sms.entity.SmsTemplate;
import com.lzy.cloud.sms.mapper.SmsTemplateMapper;
import com.lzy.cloud.sms.service.ISmsTemplateService;
import com.lzy.platform.base.utils.BeanCopierUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 短信配置表 服务实现类
 * </p>
 *
 * @author GitEgg
 * @since 2022-05-24
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SmsTemplateServiceImpl extends ServiceImpl<SmsTemplateMapper, SmsTemplate> implements ISmsTemplateService {

    private final SmsTemplateMapper smsTemplateMapper;

    /**
    * 分页查询短信配置表列表
    * @param page
    * @param querySmsTemplateDTO
    * @return
    */
    @Override
    public Page<SmsTemplateDTO> querySmsTemplateList(Page<SmsTemplateDTO> page, QuerySmsTemplateDTO querySmsTemplateDTO) {
        Page<SmsTemplateDTO> smsTemplateInfoList = smsTemplateMapper.querySmsTemplateList(page, querySmsTemplateDTO);
        return smsTemplateInfoList;
    }

    /**
    * 查询短信配置表列表
    * @param querySmsTemplateDTO
    * @return
    */
    @Override
    public List<SmsTemplateDTO> querySmsTemplateList(QuerySmsTemplateDTO querySmsTemplateDTO) {
        MPJLambdaWrapper<SmsTemplate> smsTemplateMPJLambdaWrapper = SmsTemplate.mpj_lqw()
                .selectAll(SmsTemplate.class)
                .selectAll(SmsChannel.class)
                .innerJoin(SmsChannel.class, SmsChannel::getId, SmsTemplate::getChannelId)
                .like(ObjectUtil.isNotNull(querySmsTemplateDTO.getSmsName()),SmsTemplate::getSmsName, querySmsTemplateDTO.getSmsName())
                .like(ObjectUtil.isNotNull(querySmsTemplateDTO.getSmsCode()),SmsTemplate::getSmsCode, querySmsTemplateDTO.getSmsCode())
                .eq(ObjectUtil.isNotNull(querySmsTemplateDTO.getChannelId()),SmsTemplate::getChannelId, querySmsTemplateDTO.getChannelId())
                .eq(ObjectUtil.isNotNull(querySmsTemplateDTO.getTemplateStatus()),SmsTemplate::getTemplateStatus, querySmsTemplateDTO.getTemplateStatus())
                .eq(ObjectUtil.isNotNull(querySmsTemplateDTO.getTemplateId()),SmsTemplate::getId, querySmsTemplateDTO.getTemplateId())
                .eq(ObjectUtil.isNotNull(querySmsTemplateDTO.getChannelStatus()),SmsChannel::getChannelStatus, querySmsTemplateDTO.getChannelStatus())
                .between(ObjectUtil.isNotNull(querySmsTemplateDTO.getBeginDateTime()) && ObjectUtil.isNotNull(querySmsTemplateDTO.getEndDateTime()), SmsTemplate::getCreateTime, querySmsTemplateDTO.getBeginDateTime(), querySmsTemplateDTO.getEndDateTime())
                ;
        List<SmsTemplateDTO> list = smsTemplateMapper.selectJoinList(SmsTemplateDTO.class, smsTemplateMPJLambdaWrapper);
        return list;
    }

    /**
    * 查询短信配置表详情
    * @param querySmsTemplateDTO
    * @return
    */
    @Override
    public SmsTemplateDTO querySmsTemplate(QuerySmsTemplateDTO querySmsTemplateDTO) {
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.querySmsTemplate(querySmsTemplateDTO);
        return smsTemplateDTO;
    }

    /**
    * 创建短信配置表
    * @param smsTemplate
    * @return
    */
    @Override
    public boolean createSmsTemplate(CreateSmsTemplateDTO smsTemplate) {
        SmsTemplate smsTemplateEntity = BeanCopierUtils.copyByClass(smsTemplate, SmsTemplate.class);
        boolean result = this.save(smsTemplateEntity);
        return result;
    }

    /**
    * 更新短信配置表
    * @param smsTemplate
    * @return
    */
    @Override
    public boolean updateSmsTemplate(UpdateSmsTemplateDTO smsTemplate) {
        SmsTemplate smsTemplateEntity = BeanCopierUtils.copyByClass(smsTemplate, SmsTemplate.class);
        boolean result = this.updateById(smsTemplateEntity);
        return result;
    }

    /**
    * 删除短信配置表
    * @param smsTemplateId
    * @return
    */
    @Override
    public boolean deleteSmsTemplate(Long smsTemplateId) {
        boolean result = this.removeById(smsTemplateId);
        return result;
    }

    /**
    * 批量删除短信配置表
    * @param smsTemplateIds
    * @return
    */
    @Override
    public boolean batchDeleteSmsTemplate(List<Long> smsTemplateIds) {
        boolean result = this.removeByIds(smsTemplateIds);
        return result;
    }
}
