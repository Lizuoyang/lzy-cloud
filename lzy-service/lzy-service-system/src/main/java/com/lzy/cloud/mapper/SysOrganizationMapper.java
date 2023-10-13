package com.lzy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzy.cloud.entity.SysOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织表 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2023-05-08
 */
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {
    /**
     * 查询组织机构树
     * @param organization
     * @return
     */
    List<SysOrganization> selectOrganizationChildren(@Param("org") SysOrganization organization);

    /**
     * 级联查询组织机构
     * @param organization
     * @return
     */
    List<SysOrganization> selectOrganizationList(@Param("org") SysOrganization organization);
}
