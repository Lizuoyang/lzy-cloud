package com.lzy.cloud.sms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.sms.dto.CreateSmsChannelDTO;
import com.lzy.cloud.sms.dto.QuerySmsChannelDTO;
import com.lzy.cloud.sms.dto.SmsChannelDTO;
import com.lzy.cloud.sms.dto.UpdateSmsChannelDTO;
import com.lzy.cloud.sms.entity.SmsChannel;
import com.lzy.cloud.sms.mapper.SmsChannelMapper;
import com.lzy.cloud.sms.service.ISmsChannelService;
import com.lzy.platform.base.utils.BeanCopierUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 短信渠道表 服务实现类
 * </p>
 *
 * @author GitEgg
 * @since 2022-05-24
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SmsChannelServiceImpl extends ServiceImpl<SmsChannelMapper, SmsChannel> implements ISmsChannelService {

    private final SmsChannelMapper smsChannelMapper;

    /**
    * 分页查询短信渠道表列表
    * @param page
    * @param querySmsChannelDTO
    * @return
    */
    @Override
    public Page<SmsChannelDTO> querySmsChannelList(Page<SmsChannelDTO> page, QuerySmsChannelDTO querySmsChannelDTO) {
        Page<SmsChannelDTO> smsChannelInfoList = smsChannelMapper.querySmsChannelList(page, querySmsChannelDTO);
        return smsChannelInfoList;
    }

    /**
    * 查询短信渠道表列表
    * @param querySmsChannelDTO
    * @return
    */
    @Override
    public List<SmsChannelDTO> querySmsChannelList(QuerySmsChannelDTO querySmsChannelDTO) {
        List<SmsChannelDTO> smsChannelInfoList = smsChannelMapper.querySmsChannelList(querySmsChannelDTO);
        return smsChannelInfoList;
    }

    /**
    * 查询短信渠道表详情
    * @param querySmsChannelDTO
    * @return
    */
    @Override
    public SmsChannelDTO querySmsChannel(QuerySmsChannelDTO querySmsChannelDTO) {
        SmsChannelDTO smsChannelDTO = smsChannelMapper.querySmsChannel(querySmsChannelDTO);
        return smsChannelDTO;
    }

    /**
    * 创建短信渠道表
    * @param smsChannel
    * @return
    */
    @Override
    public boolean createSmsChannel(CreateSmsChannelDTO smsChannel) {
        SmsChannel smsChannelEntity = BeanCopierUtils.copyByClass(smsChannel, SmsChannel.class);
        boolean result = this.save(smsChannelEntity);
        return result;
    }

    /**
    * 更新短信渠道表
    * @param smsChannel
    * @return
    */
    @Override
    public boolean updateSmsChannel(UpdateSmsChannelDTO smsChannel) {
        SmsChannel smsChannelEntity = BeanCopierUtils.copyByClass(smsChannel, SmsChannel.class);
        boolean result = this.updateById(smsChannelEntity);
        return result;
    }

    /**
    * 删除短信渠道表
    * @param smsChannelId
    * @return
    */
    @Override
    public boolean deleteSmsChannel(Long smsChannelId) {
        boolean result = this.removeById(smsChannelId);
        return result;
    }

    /**
    * 批量删除短信渠道表
    * @param smsChannelIds
    * @return
    */
    @Override
    public boolean batchDeleteSmsChannel(List<Long> smsChannelIds) {
        boolean result = this.removeByIds(smsChannelIds);
        return result;
    }
}
