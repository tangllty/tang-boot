package com.tang.commons.utils.poi;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.CaseFormat;
import com.tang.commons.annotation.poi.Excel;
import com.tang.commons.annotation.poi.Excel.Type;
import com.tang.commons.constants.ContentType;
import com.tang.commons.constants.FileType;
import com.tang.commons.exception.file.FileNotExistException;
import com.tang.commons.exception.file.FileTypeMismatchException;
import com.tang.commons.model.SysDictDataModel;
import com.tang.commons.utils.DictUtils;
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
     * 导入 Excel
     *
     * @param clazz 类
     * @param file  文件
     * @return 数据
     */
    public static <T> List<T> importExcel(Class<T> clazz, MultipartFile file) {
        if (Objects.isNull(file)) {
            LOGGER.error("导入失败, 文件为空");
            throw new FileNotExistException("导入失败, 文件为空");
        }
        var fileName = file.getOriginalFilename();
        if (Objects.isNull(fileName) || !fileName.endsWith(FileType.EXCEL_2007)) {
            LOGGER.error("导入失败, 只支持 Excel 2007 及以上版本");
            throw new FileTypeMismatchException("导入失败, 只支持 Excel 2007 及以上版本");
        }

        var list = new ArrayList<T>();
        var fields = getFields(clazz);

        try {
            var workbook = new XSSFWorkbook(file.getInputStream());
            var sheet = workbook.getSheetAt(0);
            var rowNum = sheet.getLastRowNum();
            for (int i = 1; i <= rowNum; i++) {
                var row = sheet.getRow(i);
                var obj = clazz.getDeclaredConstructor().newInstance();
                var cellNumIndex = new AtomicInteger();
                fields.forEach((field, excel) -> {
                    var cell = row.getCell(cellNumIndex.getAndIncrement());
                    if (excel.type() != Type.EXPORT) {
                        try {
                            ReflectionUtils.makeAccessible(field);
                            var setMethod = clazz.getMethod("set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, field.getName()), field.getType());
                            ReflectionUtils.invokeMethod(setMethod, obj, getCellValue(field, cell, excel));
                        } catch (ReflectiveOperationException e) {
                            LOGGER.error("设置值异常", e);
                        }
                    }
                });
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.error("导入 Excel 异常", e);
        }
        return list;
    }

    /**
     * 获取单元格值
     *
     * @param field 字段
     * @param cell  单元格
     * @param excel 注解
     * @return 值
     */
    private static Object getCellValue(Field field, XSSFCell cell, Excel excel) {
        if (Objects.isNull(cell)) {
            return null;
        }

        return switch(field.getType().getName()) {
            case "java.lang.Long" -> (long) cell.getNumericCellValue();
            case "java.lang.String" -> {
                var dictType = excel.dictType();
                if (StringUtils.isNotBlank(dictType)) {
                    yield DictUtils.getDictValue(dictType, cell.getStringCellValue());
                }
                yield cell.getStringCellValue();
            }
            case "java.time.LocalDateTime" -> {
                if (StringUtils.isBlank(cell.getStringCellValue())) {
                    yield null;
                }
                yield LocalDateTime.parse(cell.getStringCellValue(), DateTimeFormatter.ofPattern(excel.dateFormat()));
            }
            default -> throw new IllegalStateException("Unexpected type name: " + field.getType().getName());
        };
    }

    /**
     * 导出 Excel
     *
     * @param response 响应
     * @param clazz    类
     * @param list     数据
     */
    public static <T> void export(HttpServletResponse response, Class<T> clazz, List<T> list) {
        var maxRowNum = 65535;
        var sheetNum = (list.size() + maxRowNum) / maxRowNum;
        var map = new LinkedHashMap<String, List<T>>(sheetNum);
        for (int i = 0; i < sheetNum; i++) {
            var formIndex = i * maxRowNum;
            var toIndex = Math.min(formIndex + maxRowNum, list.size());
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
     */
    public static <T> void export(HttpServletResponse response, Class<T> clazz, Map<String, List<T>> map) {
        var workbook = new XSSFWorkbook();

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
    private static <T> void setData(LinkedHashMap<Field, Excel> fields, List<T> list, XSSFSheet sheet) {
        list.forEach(clazz -> {
            var row = sheet.createRow(sheet.getLastRowNum() + 1);
            fields.forEach((field, excel) -> {
                var lastCellNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                var cell = row.createCell(lastCellNum);
                // 设置行高
                row.setHeight((short) (excel.height() * 20));
                setCellValue(cell, clazz, field, excel);
            });
        });
    }


    /**
     * 设置单元格值
     *
     * @param cell  单元格
     * @param clazz 对象
     * @param field 字段
     * @param excel 注解
     */
    private static <T> void setCellValue(XSSFCell cell, T clazz, Field field, Excel excel) {
        try {
            ReflectionUtils.makeAccessible(field);
            var stringValue = Objects.isNull(field.get(clazz)) ? "" : field.get(clazz).toString();
            switch (excel.cellType()) {
                case STRING -> {
                    var dictType = excel.dictType();
                    if (StringUtils.isNotBlank(dictType)) {
                        var dictDataList = DictUtils.getDictDataList(dictType);
                        var validationHelper = new XSSFDataValidationHelper(cell.getSheet());
                        var validationConstraint = validationHelper.createExplicitListConstraint(dictDataList.stream().map(SysDictDataModel::getDataLabel).toList().toArray(new String[0]));
                        var cellRangeAddressList = new CellRangeAddressList(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex());
                        var validation = validationHelper.createValidation(validationConstraint, cellRangeAddressList);
                        cell.setCellValue(DictUtils.getDictLabel(dictType, stringValue));
                        cell.getSheet().addValidationData(validation);
                    } else {
                        cell.setCellValue(stringValue);
                    }
                }
                case NUMBER -> cell.setCellValue(Double.parseDouble(stringValue));
                case DATE -> {
                    if (StringUtils.isNotBlank(stringValue)) {
                        var formatter = DateTimeFormatter.ofPattern(excel.dateFormat());
                        var formattedDate = formatter.format(LocalDateTime.parse(stringValue));
                        cell.setCellValue(formattedDate);
                    }
                }
                default -> throw new IllegalArgumentException("不支持的单元格类型: " + excel.cellType());
            }
        } catch (Exception e) {
            LOGGER.error("获取值异常", e);
        }
    }

    /**
     * 设置标题
     *
     * @param fields 字段
     * @param sheet  工作表
     */
    private static void setTitle(LinkedHashMap<Field, Excel> fields, XSSFSheet sheet) {
        var titleRow = sheet.createRow(0);
        fields.forEach((field, excel) -> {
            var lastCellNum = titleRow.getLastCellNum() == -1 ? 0 : titleRow.getLastCellNum();
            var cell = titleRow.createCell(lastCellNum);
            cell.setCellValue(excel.name());

            // 设置标题样式
            var cellStyle = sheet.getWorkbook().createCellStyle();
            // 设置字体
            var font = sheet.getWorkbook().createFont();
            font.setBold(true);
            cellStyle.setFont(font);
            // 设置背景色
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // 设置边框
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            // 设置样式
            cell.setCellStyle(cellStyle);
            // 设置列宽
            sheet.setColumnWidth(lastCellNum, excel.width() * 256);
            // 设置行高
            titleRow.setHeight((short) (excel.titleHeight() * 20));
        });
    }

    /**
     * 获取所有字段
     *
     * @param clazz 类
     * @return LinkedHashMap
     */
    private static <T> LinkedHashMap<Field, Excel> getFields(Class<T> clazz) {
        var fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));

        return fields.stream()
            .filter(field -> field.isAnnotationPresent(Excel.class))
            .collect(Collectors.toMap(field -> field, field -> AnnotationUtils.getAnnotation(field, Excel.class), (k1, k2) -> k1, LinkedHashMap::new))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(Excel::sort)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k1, k2) -> k1, LinkedHashMap::new));
    }

    /**
     * 响应 Excel
     *
     * @param workbook 工作簿
     * @param response 响应
     */
    private static void response(HttpServletResponse response, XSSFWorkbook workbook) {
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
