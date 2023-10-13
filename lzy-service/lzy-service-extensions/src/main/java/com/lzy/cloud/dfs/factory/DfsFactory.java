package com.lzy.cloud.dfs.factory;

import com.lzy.cloud.dfs.dto.DfsDTO;
import com.lzy.cloud.dfs.enums.DfsFactoryClassEnum;
import com.lzy.platform.constants.DfsConstants;
import com.lzy.platform.service.IDfsBaseService;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DfsFactory工厂类,根据系统用户配置，生成及缓存对应的上传下载接口实现
 */
@Component
public class DfsFactory {

    /**
     * DfsService 缓存
     */
    private final static Map<Long, IDfsBaseService> dfsBaseServiceMap = new ConcurrentHashMap<>();

    /**
     * 获取 DfsService
     *
     * @param dfsDTO 分布式存储配置
     * @return dfsService
     */
    public IDfsBaseService getDfsBaseService(DfsDTO dfsDTO) {
        //根据dfsId获取对应的分布式存储服务接口，dfsId是唯一的，每个租户有其自有的dfsId
        Long dfsId = dfsDTO.getId();
        IDfsBaseService dfsBaseService = dfsBaseServiceMap.get(dfsId);
        if (null == dfsBaseService) {
            Class cls = null;
            try {
                cls = Class.forName(DfsFactoryClassEnum.getValue(String.valueOf(dfsDTO.getDfsType())));
                Method staticMethod = cls.getDeclaredMethod(DfsConstants.DFS_SERVICE_FUNCTION, DfsDTO.class);
                dfsBaseService = (IDfsBaseService) staticMethod.invoke(cls, dfsDTO);
                dfsBaseServiceMap.put(dfsId, dfsBaseService);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return dfsBaseService;
    }

}
