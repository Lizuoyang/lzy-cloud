package com.lzy.cloud.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.entity.SysOrganization;
import com.lzy.cloud.mapper.SysOrganizationMapper;
import com.lzy.cloud.service.ISysOrganizationService;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.base.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织表 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2023-05-08
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationMapper, SysOrganization> implements ISysOrganizationService {
    private final SysOrganizationMapper organizationMapper;

    /**
     * queryOrgList
     *
     * @Title: queryOrgList
     * @Description: 查询所有的组织结构树
     * @param parentId
     * @return List<SysOrganization>
     */
    @Override
    public List<SysOrganization> queryOrgList(Long parentId) {
        List<SysOrganization> orgList;
        try {
            if (null == parentId) {
                parentId = LzyCloudConstant.PARENT_ID;
            }
            SysOrganization organizationParent = new SysOrganization();
            organizationParent.setParentId(parentId);
            orgList = organizationMapper.selectOrganizationChildren(organizationParent);
        } catch (Exception e) {
            log.error("查询组织树失败:", e);
            throw new BusinessException("查询组织树失败");
        }
        return orgList;
    }

    @Override
    public List<SysOrganization> queryOrganizationByParentId(SysOrganization organization) {
        List<SysOrganization> orgs;
        try {
            if (null == organization.getParentId()) {
                organization.setParentId(LzyCloudConstant.PARENT_ID);
            }
            List<SysOrganization> orgList = organizationMapper.selectOrganizationChildren(organization);
            Map<Long, SysOrganization> organizationMap = new HashMap<>();
            orgs = this.assembleOrganizationTree(orgList, organizationMap);
        } catch (Exception e) {
            log.error("查询组织树失败:", e);
            throw new BusinessException("查询组织树失败");
        }
        return orgs;
    }

    @Override
    public List<SysOrganization> queryOrganizationList(SysOrganization organization) {
        try {
            if (null == organization.getParentId()) {
                organization.setParentId(LzyCloudConstant.PARENT_ID);
            }
            return organizationMapper.selectOrganizationList(organization);
        } catch (Exception e) {
            log.error("查询组织树失败:", e);
            throw new BusinessException("查询组织树失败");
        }
    }

    @Override
    public boolean createOrganization(SysOrganization organization) {
        LambdaQueryWrapper<SysOrganization> ew = new LambdaQueryWrapper<>();
        ew.eq(SysOrganization::getOrganizationName, organization.getOrganizationName()).or().eq(SysOrganization::getOrganizationKey, organization.getOrganizationKey());
        List<SysOrganization> organizationList = list(ew);
        if (!CollectionUtils.isEmpty(organizationList)) {
            throw new BusinessException("组织名称或组织标识已经存在");
        }

        if(null != organization.getParentId() && organization.getParentId().longValue() != LzyCloudConstant.PARENT_ID.longValue())
        {
            SysOrganization organizationParent = this.getById(organization.getParentId());
            String parentAncestors = organizationParent.getAncestors();
            organization.setAncestors(parentAncestors + StrUtil.COMMA + organization.getParentId());
        }
        else
        {
            organization.setAncestors(LzyCloudConstant.PARENT_ID.toString());
        }

        boolean result = save(organization);
        return result;
    }

    @Override
    public boolean updateOrganization(SysOrganization organization) {
        LambdaQueryWrapper<SysOrganization> ew = new LambdaQueryWrapper<>();
        ew.ne(SysOrganization::getId, organization.getId()).and(e -> e.eq(SysOrganization::getOrganizationName, organization.getOrganizationName()).or().eq(SysOrganization::getOrganizationKey, organization.getOrganizationKey()));
        List<SysOrganization> organizationList = list(ew);
        if (!CollectionUtils.isEmpty(organizationList)) {
            throw new BusinessException("组织名称或组织标识已经存在");
        }

        //判断是否修改了父级组织ID，如果改了，那么需要修改本机构及本机构下所有的ancestors字段
        SysOrganization organizationOld = this.getById(organization.getId());
        if (null != organizationOld && null != organizationOld.getParentId()
                && null != organization.getParentId() && organizationOld.getParentId().longValue() != organization.getParentId().longValue())
        {
            SysOrganization organizationParentNew = this.getById(organization.getParentId());
            //新的父级组合
            String ancestorsNew = null == organizationParentNew ? LzyCloudConstant.PARENT_ID.toString() : organizationParentNew.getAncestors() + StrUtil.COMMA + organization.getParentId();
            //设置组织新的父级组合
            organization.setAncestors(ancestorsNew);
            //旧的父级组合
            String ancestorsOld = organizationOld.getAncestors();

            SysOrganization organizationParent = new SysOrganization();
            organizationParent.setParentId(organization.getId());
            //只查子节点
            organizationParent.setIsLeaf(LzyCloudConstant.Number.ONE);
            List<SysOrganization> orgChildrenList = organizationMapper.selectOrganizationChildren(organizationParent);
            if (!CollectionUtils.isEmpty(orgChildrenList)) {
                orgChildrenList = orgChildrenList.stream().map(org -> {org.setAncestors(org.getAncestors().replaceFirst(ancestorsOld, ancestorsNew)); return org;}).collect(Collectors.toList());
            }
            this.updateBatchById(orgChildrenList);
        }

        boolean result = updateById(organization);
        return result;
    }

    @Override
    public boolean deleteOrganization(Long organizationId) {
        boolean result = false;
        if (null == organizationId)
        {
            throw new BusinessException("请选择要删除的组织");
        }
        SysOrganization organizationParent = new SysOrganization();
        organizationParent.setParentId(organizationId);
        List<SysOrganization> orgChildrenList = organizationMapper.selectOrganizationChildren(organizationParent);
        if (!CollectionUtils.isEmpty(orgChildrenList))
        {
            List<Long> orgIds = orgChildrenList.stream().map(SysOrganization::getId).collect(Collectors.toList());
            result = removeByIds(orgIds);
        }
        return result;
    }

    /**
     * 组装子父级目录
     * @param organizationList
     * @param organizationMap
     * @return
     */
    private List<SysOrganization> assembleOrganizationTree(List<SysOrganization> organizationList, Map<Long, SysOrganization> organizationMap)
    {
        List<SysOrganization> organizations = new ArrayList<>();
        for (SysOrganization organization : organizationList) {
            organizationMap.put(organization.getId(), organization);
        }
        for (SysOrganization organization : organizationList) {
            Long treePId = organization.getParentId();
            SysOrganization organizationTree = organizationMap.get(treePId);
            if (null != organizationTree && !organization.equals(organizationTree)) {
                List<SysOrganization> nodes = organizationTree.getChildren();
                if (null == nodes) {
                    nodes = new ArrayList<>();
                    organizationTree.setChildren(nodes);
                }
                nodes.add(organization);
            } else {
                organizations.add(organization);
            }
        }
        return organizations;
    }
}
