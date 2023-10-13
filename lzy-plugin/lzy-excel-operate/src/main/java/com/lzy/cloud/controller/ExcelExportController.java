package com.lzy.cloud.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.lzy.cloud.convert.SystemUserExportConvert;
import com.lzy.cloud.dto.QueryUserDTO;
import com.lzy.cloud.export.system.SystemUserExport;
import com.lzy.cloud.feign.IUserFeign;
import com.lzy.platform.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * excel导出API
 *
 * @author lizuoyang
 * @date 2023/01/09
 */
@Api(tags = "excel导出中心")
@Slf4j
@RestController
@RequestMapping("/export/")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExcelExportController {
    private final IUserFeign userFeign;

    @ApiOperation("用户管理列表导出")
    @PostMapping("/system/user/list")
    public void systemUserListExport(HttpServletResponse response, @ApiIgnore @RequestBody QueryUserDTO queryUserDTO) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String sheetName = "用户列表";
        String fileName = URLEncoder.encode(sheetName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), SystemUserExport.class)
                // 自动列宽
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build();
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();

        int limit = 1000;
        int offest = 0;
        while (true) {
            Result<Object> result = userFeign.listUsers(queryUserDTO);
            List<LinkedHashMap> collect = (ArrayList)result.getData();
            List<SystemUserExport> systemUserExports = SystemUserExportConvert.INSTANCE.toTargetList(collect);
            excelWriter.write(systemUserExports, writeSheet);
            offest += systemUserExports.size();
            if (offest % 1000000 == 0) {
                writeSheet = EasyExcel.writerSheet(sheetName + offest / 1000000).build();
            }
            if (systemUserExports.size() < limit) {
                break;
            }
        }
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }
}
