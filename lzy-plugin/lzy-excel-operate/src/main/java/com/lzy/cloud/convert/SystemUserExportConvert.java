package com.lzy.cloud.convert;

import com.lzy.cloud.export.system.SystemUserExport;
import com.lzy.platform.base.convert.BaseObjectConvert;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 系统用户excel导出转换类
 *
 * @author lizuoyang
 * @date 2023/01/10
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD)
public interface SystemUserExportConvert extends BaseObjectConvert<LinkedHashMap, SystemUserExport> {

    SystemUserExportConvert INSTANCE = Mappers.getMapper(SystemUserExportConvert.class);

    @Override
    @Mapping(target = "account", expression = "java( (String)map.get(\"account\") )")
    @Mapping(target = "nickname", expression = "java( (String)map.get(\"nickname\") )")
    @Mapping(target = "realName", expression = "java( (String)map.get(\"realName\") )")
    @Mapping(target = "mobile", expression = "java( (String)map.get(\"mobile\") )")
    @Mapping(target = "email", expression = "java( (String)map.get(\"email\") )")
    @Mapping(target = "roleName", expression = "java( (String)map.get(\"roleName\") )")
    @Mapping(target = "gender", expression = "java( (String)map.get(\"gender\") )")
    @Mapping(target = "createTime", expression = "java( (String)map.get(\"createTime\") )")
    @Mapping(target = "status", expression = "java( (Integer)map.get(\"status\") )")
    SystemUserExport toTarget(LinkedHashMap map);

    @Override
    List<SystemUserExport> toTargetList(List<LinkedHashMap> sourceList);
}
