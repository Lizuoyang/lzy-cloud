package com.lzy.cloud.export.convert;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 系统用户状态转换器
 *
 * @author lizuoyang
 * @date 2023/01/10
 */
public class StatusConverter implements Converter<Integer> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) {
        return context.getReadCellData().getNumberValue().intValue();
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(getStatus(value));
    }

    private String getStatus(Integer value) {
        String result = "";
        switch (value) {
            case 0:
                result = "禁用";
                break;
            case 1:
                result = "启用";
                break;
            case 2:
                result = "密码过期或初次未修改";
                break;
            default:
                break;
        }
        return result;
    }
}
