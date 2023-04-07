package com.tang.commons.utils.poi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import com.tang.commons.annotation.poi.Excel;
import com.tang.commons.constants.ContentType;
import com.tang.commons.utils.LogUtils;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Excel 工具类
 *
 * @author Tang
 */
public class ExcelUtils {

    private ExcelUtils() {
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 导出 Excel
     *
     * @param response 响应
     * @param clazz    类
     * @param list     数据
     * @param fileName 文件名
     */
    public static void export(HttpServletResponse response, Class<?> clazz, List<?> list) {
        var maxRowNum = 65535;
        var sheetNum = (list.size() + maxRowNum) / maxRowNum;
        var map = new LinkedHashMap<String, List<?>>(sheetNum);
        for (int i = 0; i < sheetNum; i++) {
            var formIndex = i * maxRowNum;
            var toIndex = formIndex + maxRowNum > list.size() ? list.size() : formIndex + maxRowNum;
            map.put("sheet" + i, list.subList(formIndex, toIndex));
        }

        export(response, clazz, map);
    }

    /**
     * 导出 Excel
     *
     * @param response 响应
     * @param clazz    类
     * @param map      数据(key sheet name, value data, 使用 LinkedHashMap 保证顺序)
     * @param fileName 文件名
     */
    public static void export(HttpServletResponse response, Class<?> clazz, Map<String, List<?>> map) {
        var workbook = new HSSFWorkbook();

        var fields = getFields(clazz);

        map.forEach((sheetName, list) -> {
            var sheet = workbook.createSheet(sheetName);

            setTitle(fields, sheet);
            setData(fields, list, sheet);
        });
        response(response, workbook);
    }

    /**
     * 设置数据
     *
     * @param fields 字段
     * @param list   数据
     * @param sheet  工作表
     */
    private static void setData(LinkedHashMap<Field, Excel> fields, List<?> list, HSSFSheet sheet) {
        list.forEach(o -> {
            var row = sheet.createRow(sheet.getLastRowNum() + 1);
            fields.forEach((field, excel) -> {
                var lastCellNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                var cell = row.createCell(lastCellNum);
                try {
                    ReflectionUtils.makeAccessible(field);
                    cell.setCellValue(field.get(o) == null ? "" : field.get(o).toString());
                } catch (Exception e) {
                    LOGGER.error("获取值异常", e);
                }
            });
        });
    }

    /**
     * 设置标题
     *
     * @param fields 字段
     * @param sheet  工作表
     */
    private static void setTitle(LinkedHashMap<Field, Excel> fields, HSSFSheet sheet) {
        var titleRow = sheet.createRow(0);
        fields.forEach((field, excel) -> {
            var lastCellNum = titleRow.getLastCellNum() == -1 ? 0 : titleRow.getLastCellNum();
            var cell = titleRow.createCell(lastCellNum);
            cell.setCellValue(excel.name());
        });
    }

    /**
     * 获取所有字段
     *
     * @param clazz 类
     * @return LinkedHashMap
     */
    private static LinkedHashMap<Field, Excel> getFields(Class<?> clazz) {
        var fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));

        return fields.stream()
            .filter(field -> field.isAnnotationPresent(Excel.class))
            .collect(Collectors.toMap(field -> field, field -> AnnotationUtils.getAnnotation(field, Excel.class), (k1, k2) -> k1, LinkedHashMap::new));
    }

    /**
     * 响应 Excel
     *
     * @param workbook 工作簿
     * @param fileName 文件名
     * @param response 响应
     */
    private static void response(HttpServletResponse response, HSSFWorkbook workbook) {
        response.setContentType(ContentType.APPLICATION_XLSX);
        response.setHeader("Content-Disposition", "attachment; filename="+ System.currentTimeMillis() +".xlsx");
        try {
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("导出 Excel 异常", e);
        } finally {
            try {
                workbook.close();
            } catch (Exception e) {
                LOGGER.error("关闭 Excel 异常", e);
            }
        }
    }

}
