package com.lzy.cloud.export.convert;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 系统用户性别转换器
 *
 * @author lizuoyang
 * @date 2023/01/10
 */
public class GenderConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 读的时候会调用
     *
     * @return
     */
    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        return context.getReadCellData().getStringValue();
    }

    /**
     * 写的时候会调用
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(getGender(value));
    }

    private String getGender(String value) {
        String result = ObjectUtil.equals(value, "0") ? "女" : "男";
        return result;
    }
}
