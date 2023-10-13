package com.lzy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzy.cloud.dto.QueryUserResourceDTO;
import com.lzy.cloud.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查询用户权限资源
     * @param queryUserResourceDTO
     * @return
     */
    List<Resource> queryResourceByUserId(@Param("userResource") QueryUserResourceDTO queryUserResourceDTO);

    /**
     *
     * 查询所有资源
     * @param resourceParent
     * @return
     */
    List<Resource> selectResourceChildren(@Param("resource") Resource resourceParent);

    /**
     * 查询拥有权限资源的角色
     *
     * @param
     * @return
     */
    List<Resource> queryResourceRoles();
}
