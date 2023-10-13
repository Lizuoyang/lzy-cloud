package com.lzy.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzy.cloud.entity.SysOrganization;

import java.util.List;

/**
 * <p>
 * 组织表 服务类
 * </p>
 *
 * @author lzy
 * @since 2023-05-08
 */
public interface ISysOrganizationService extends IService<SysOrganization> {
    /**
     * 查询机构列表
     * @param organization
     * @return
     */
    List<SysOrganization> queryOrganizationByParentId(SysOrganization organization);

    /**
     * 查询机构列表，不组装父子节点
     * @param parentId
     * @return
     */
    List<SysOrganization> queryOrgList(Long parentId);

    /**
     * 级联查询组织机构
     * @param organization
     * @return
     */
    List<SysOrganization> queryOrganizationList(SysOrganization organization);

    /**
     * 创建组织
     * @param organization
     * @return
     */
    boolean createOrganization(SysOrganization organization);

    /**
     * 更新组织
     * @param organization
     * @return
     */
    boolean updateOrganization(SysOrganization organization);

    /**
     * 删除组织
     * @param organizationId
     * @return
     */
    boolean deleteOrganization(Long organizationId);
}
