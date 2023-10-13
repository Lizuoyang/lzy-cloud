package com.lzy.cloud.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.sms.dto.QuerySmsChannelDTO;
import com.lzy.cloud.sms.dto.SmsChannelDTO;
import com.lzy.cloud.sms.entity.SmsChannel;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 短信渠道表 Mapper 接口
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
public interface SmsChannelMapper extends BaseMapper<SmsChannel> {

    /**
    * 分页查询短信渠道表列表
    * @param page
    * @param smsChannelDTO
    * @return
    */
    Page<SmsChannelDTO> querySmsChannelList(Page<SmsChannelDTO> page, @Param("smsChannel") QuerySmsChannelDTO smsChannelDTO);

    /**
    * 查询短信渠道表列表
    * @param smsChannelDTO
    * @return
    */
    List<SmsChannelDTO> querySmsChannelList(@Param("smsChannel") QuerySmsChannelDTO smsChannelDTO);

    /**
    * 查询短信渠道表信息
    * @param smsChannelDTO
    * @return
    */
    SmsChannelDTO querySmsChannel(@Param("smsChannel") QuerySmsChannelDTO smsChannelDTO);
}
