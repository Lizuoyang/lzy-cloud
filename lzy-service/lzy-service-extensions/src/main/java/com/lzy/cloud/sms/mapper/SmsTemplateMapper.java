package com.lzy.cloud.sms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import com.lzy.cloud.sms.dto.QuerySmsTemplateDTO;
import com.lzy.cloud.sms.dto.SmsTemplateDTO;
import com.lzy.cloud.sms.entity.SmsTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 短信配置表 Mapper 接口
 *
 * @author lizuoyang
 * @date 2023/07/13
 */
public interface SmsTemplateMapper extends MPJBaseMapper<SmsTemplate> {

    /**
    * 分页查询短信配置表列表
    * @param page
    * @param smsTemplateDTO
    * @return
    */
    Page<SmsTemplateDTO> querySmsTemplateList(Page<SmsTemplateDTO> page, @Param("smsTemplate") QuerySmsTemplateDTO smsTemplateDTO);

    /**
    * 查询短信配置表列表
    * @param smsTemplateDTO
    * @return
    */
    List<SmsTemplateDTO> querySmsTemplateList(@Param("smsTemplate") QuerySmsTemplateDTO smsTemplateDTO);

    /**
    * 查询短信配置表信息
    * @param smsTemplateDTO
    * @return
    */
    SmsTemplateDTO querySmsTemplate(@Param("smsTemplate") QuerySmsTemplateDTO smsTemplateDTO);
}
