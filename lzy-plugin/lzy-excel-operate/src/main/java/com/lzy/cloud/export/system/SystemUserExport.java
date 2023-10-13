package com.lzy.cloud.export.system;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lzy.cloud.export.convert.GenderConverter;
import com.lzy.cloud.export.convert.StatusConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户列表导出类
 *
 * @author lizuoyang
 * @date 2023/01/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserExport implements Serializable {
    private static final long serialVersionUID = -4521939003520213378L;

    @ExcelProperty(value = "账号")
    private String account;
    @ExcelProperty(value = "昵称")
    private String nickname;
    @ExcelProperty(value = "姓名")
    private String realName;
    @ExcelProperty(value = "手机号")
    private String mobile;
    @ExcelProperty(value = "邮箱")
    private String email;
    @ExcelProperty(value = "角色")
    private String roleName;
    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    private String gender;
    @ExcelProperty(value = "注册时间")
    private String createTime;
    @ExcelProperty(value = "状态", converter = StatusConverter.class)
    private Integer status;
}
