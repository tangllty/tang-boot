package com.tang.commons.utils.poi

import com.google.common.base.CaseFormat
import com.tang.commons.annotation.poi.Excel
import com.tang.commons.constants.ContentType
import com.tang.commons.constants.FileType
import com.tang.commons.enumeration.poi.Type
import com.tang.commons.exception.file.FileTypeMismatchException
import com.tang.commons.utils.Assert
import com.tang.commons.utils.DictUtils
import com.tang.commons.utils.LogUtils
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddressList
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.util.ReflectionUtils
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.lang.reflect.Field
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Objects
import kotlin.math.min
import kotlin.reflect.full.memberExtensionProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import com.tang.commons.enumeration.poi.CellType as ExcelCellType

/**
 * Excel 工具类
 *
 * @author Tang
 */
object Excels {

    private val LOGGER = LogUtils.getLogger()

    /**
     * 导入 Excel
     *
     * @param clazz 类
     * @param file  文件
     * @return 数据
     */
    @JvmStatic
    fun <T> importExcel(clazz: Class<T>, file: MultipartFile): List<T> {
        val fileName = file.originalFilename ?: ""
        Assert.isFalse(fileName.endsWith(FileType.EXCEL_2007)) {
            LOGGER.error("导入失败, 只支持 Excel 2007 及以上版本")
            FileTypeMismatchException("导入失败, 只支持 Excel 2007 及以上版本")
        }
        return importExcel(clazz, file.inputStream)
    }

    /**
     * 导入 Excel
     *
     * @param clazz 类
     * @param file  文件流
     * @return 数据
     */
    @JvmStatic
    fun <T> importExcel(clazz: Class<T>, file: InputStream): List<T> {
        val list = mutableListOf<T>()
        val fields = getFields(clazz)
        val workbook = XSSFWorkbook(file)
        val sheet = workbook.getSheetAt(0)
        val rowNum = sheet.lastRowNum
        val titleIndexMap = getTitleIndex(sheet.getRow(0))
        for (i in 1..rowNum) {
            val row = sheet.getRow(i)
            val obj = clazz.getDeclaredConstructor().newInstance()
            fields.forEach { (field: Field, excel: Excel) ->
                val index = titleIndexMap[excel.name]
                if (excel.type == Type.EXPORT || index == null) {
                    return@forEach
                }
                val cell = row.getCell(index)
                ReflectionUtils.makeAccessible(field)
                val setMethod = clazz.getMethod("set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, field.name), field.type)
                val cellValue = getCellValue(field, cell, excel)
                ReflectionUtils.invokeMethod(setMethod, obj, cellValue)
            }
            list.add(obj)
        }
        return list.filter { !isNull(it) }
    }

    /**
     * 获取标题索引
     *
     * @param row 行
     * @return 标题索引
     */
    private fun getTitleIndex(row: XSSFRow): Map<String, Int> {
        val titleIndexMap = mutableMapOf<String, Int>()
        for (i in 0 until row.lastCellNum) {
            val cell = row.getCell(i)
            titleIndexMap[cell.stringCellValue] = i
        }
        return titleIndexMap
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return 是否为空
     */
    private fun isNull(obj: Any?): Boolean {
        if (obj == null) {
            return true
        }

        val properties = obj::class.memberProperties + obj::class.memberExtensionProperties
        return properties.all {
            it.isAccessible = true
            val value = it.getter.call(obj)
            value == null || value == ""
        }
    }

    /**
     * 获取单元格值
     *
     * @param field 字段
     * @param cell  单元格
     * @param excel 注解
     * @return 值
     */
    private fun getCellValue(field: Field, cell: XSSFCell?, excel: Excel): Any? {
        if (cell == null) {
            return null
        }

        return when (field.type.name) {
            "java.lang.Long" -> cell.numericCellValue.toLong()
            "java.lang.String" -> {
                val dictType = excel.dictType
                if (StringUtils.isNotBlank(dictType)) {
                    return DictUtils.getDictValue(dictType, cell.stringCellValue)
                }
                if (cell.cellType == CellType.NUMERIC) {
                    return cell.numericCellValue.toString()
                }
                return cell.stringCellValue
            }
            LocalDateTime::class.java.name -> {
                if (cell.cellType == CellType.NUMERIC) {
                    return cell.localDateTimeCellValue
                }
                if (StringUtils.isBlank(cell.stringCellValue)) {
                    return null
                }
                return LocalDateTime.parse(cell.stringCellValue, DateTimeFormatter.ofPattern(excel.dateFormat))
            }
            else -> throw IllegalStateException("Unexpected type name: " + field.type.name)
        }
    }

    /**
     * 导出 Excel 模板(默认 10 行)
     *
     * @param <T>      类型
     * @param response 响应
     * @param clazz    类
     */
    @JvmStatic
    fun <T> exportTemplate(response: HttpServletResponse, clazz: Class<T>) {
        exportTemplate(response, clazz, ArrayList(16), 10)
    }

    /**
     * 导出 Excel 模板(默认 10 行)
     *
     * @param <T>      类型
     * @param response 响应
     * @param clazz    类
     * @param list     数据
     */
    @JvmStatic
    fun <T> exportTemplate(response: HttpServletResponse, clazz: Class<T>, list: MutableList<T>) {
        exportTemplate(response, clazz, list, 10)
    }

    /**
     * 导出 Excel 模板
     *
     * @param <T>      类型
     * @param response 响应
     * @param clazz    类
     * @param list     数据
     * @param rowNum   行数
     */
    @JvmStatic
    fun <T> exportTemplate(response: HttpServletResponse, clazz: Class<T>, list: MutableList<T>, rowNum: Int) {
        for (i in 0 until rowNum) {
            list.add(clazz.getDeclaredConstructor().newInstance())
        }
        export(response, clazz, list)
    }

    /**
     * 导出 Excel 模板(默认 10 行)
     *
     * @param <T>      类型
     * @param response 响应
     * @param clazz    类
     * @param map      数据
     */
    @JvmStatic
    fun <T> exportTemplate(response: HttpServletResponse, clazz: Class<T>, map: Map<String, List<T>>) {
        exportTemplate(response, clazz, map, 10)
    }

    /**
     * 导出 Excel 模板
     *
     * @param <T>      类型
     * @param response 响应
     * @param clazz    类
     * @param map      数据
     * @param rowNum   行数
     */
    @JvmStatic
    fun <T> exportTemplate(response: HttpServletResponse, clazz: Class<T>, map: Map<String, List<T>>, rowNum: Int) {
        map.map { (sheetName: String, _) ->
            val list = mutableListOf<T>()
            for (i in 0 until rowNum) {
                list.add(clazz.getDeclaredConstructor().newInstance())
            }
            sheetName to list
        }.toMap().let {
            export(response, clazz, it)
        }
    }

    /**
     * 导出 Excel
     *
     * @param response 响应
     * @param clazz    类
     * @param list     数据
     */
    @JvmStatic
    fun <T> export(response: HttpServletResponse, clazz: Class<T>, list: List<T>) {
        val maxRowNum = 65535
        val sheetNum = (list.size + maxRowNum) / maxRowNum
        val map = LinkedHashMap<String, List<T>>(sheetNum)
        for (i in 0 until sheetNum) {
            val formIndex = i * maxRowNum
            val toIndex = min(formIndex + maxRowNum, list.size)
            map["sheet$i"] = list.subList(formIndex, toIndex)
        }
        export(response, clazz, map)
    }

    /**
     * 导出 Excel
     *
     * @param response 响应
     * @param clazz    类
     * @param map      数据(key sheet name, value data, 使用 LinkedHashMap 保证顺序)
     */
    @JvmStatic
    fun <T> export(response: HttpServletResponse, clazz: Class<T>, map: Map<String, List<T>>) {
        val workbook = XSSFWorkbook()

        val fields = getFields(clazz)
        map.forEach { (sheetName: String, list: List<T>) ->
            val sheet = workbook.createSheet(sheetName)
            setTitle(fields, sheet)
            setData(fields, list, sheet)
        }
        response(response, workbook)
    }

    /**
     * 设置数据
     *
     * @param fields 字段
     * @param list   数据
     * @param sheet  工作表
     */
    private fun <T> setData(fields: LinkedHashMap<Field, Excel>, list: List<T>, sheet: XSSFSheet) {
        list.forEach { clazz ->
            val row = sheet.createRow(sheet.lastRowNum + 1)
            fields.forEach { (field: Field, excel: Excel) ->
                val lastCellNum = if (row.lastCellNum.toInt() == -1) 0 else row.lastCellNum.toInt()
                val cell = row.createCell(lastCellNum)
                // 设置行高
                row.height = (excel.height * 20).toShort()
                setCellValue(cell, clazz, field, excel)
            }
        }
    }

    /**
     * 设置单元格值
     *
     * @param cell  单元格
     * @param clazz 对象
     * @param field 字段
     * @param excel 注解
     */
    private fun <T> setCellValue(cell: XSSFCell, clazz: T, field: Field, excel: Excel) {
        ReflectionUtils.makeAccessible(field)
        val stringValue = Objects.toString(field.get(clazz), "")
        when (excel.cellType) {
            ExcelCellType.STRING -> {
                val dictType = excel.dictType
                if (StringUtils.isBlank(dictType)) {
                    cell.setCellValue(stringValue)
                    return
                }

                val dictDataList = DictUtils.getDictDataList(dictType)
                val labelList = dictDataList.map { it.dataLabel }.toTypedArray()
                val validationHelper = XSSFDataValidationHelper(cell.sheet)
                val validationConstraint = validationHelper.createExplicitListConstraint(labelList)
                val cellRangeAddressList = CellRangeAddressList(cell.rowIndex, cell.rowIndex, cell.columnIndex, cell.columnIndex)
                val validation = validationHelper.createValidation(validationConstraint, cellRangeAddressList)
                cell.setCellValue(DictUtils.getDictLabel(dictType, stringValue))
                cell.sheet.addValidationData(validation)
            }
            ExcelCellType.NUMBER -> {
                if (StringUtils.isBlank(stringValue)) {
                    return
                }
                cell.setCellValue(stringValue.toDouble())
            }
            ExcelCellType.DATE -> {
                if (StringUtils.isBlank(stringValue)) {
                    return
                }
                val formatter = DateTimeFormatter.ofPattern(excel.dateFormat)
                val formattedDate = formatter.format(LocalDateTime.parse(stringValue))
                cell.setCellValue(formattedDate)
            }
        }
    }

    /**
     * 设置标题
     *
     * @param fields 字段
     * @param sheet  工作表
     */
    private fun setTitle(fields: LinkedHashMap<Field, Excel>, sheet: XSSFSheet) {
        val titleRow = sheet.createRow(0)
        fields.forEach { (_, excel) ->
            val lastCellNum = if (titleRow.lastCellNum.toInt() == -1) 0 else titleRow.lastCellNum.toInt()
            val cell = titleRow.createCell(lastCellNum)
            cell.setCellValue(excel.name)

            // 设置标题样式
            val cellStyle = sheet.workbook.createCellStyle()

            // 设置水平对齐方式
            cellStyle.alignment = HorizontalAlignment.CENTER

            // 设置垂直对齐方式
            cellStyle.verticalAlignment = VerticalAlignment.CENTER

            // 设置字体
            val font = sheet.workbook.createFont()
            font.bold = true
            cellStyle.setFont(font)

            // 设置背景色
            cellStyle.fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
            cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

            // 设置边框
            cellStyle.borderLeft = BorderStyle.THIN
            cellStyle.leftBorderColor = IndexedColors.BLACK.index
            cellStyle.borderTop = BorderStyle.THIN
            cellStyle.topBorderColor = IndexedColors.BLACK.index
            cellStyle.borderRight = BorderStyle.THIN
            cellStyle.rightBorderColor = IndexedColors.BLACK.index
            cellStyle.borderBottom = BorderStyle.THIN
            cellStyle.bottomBorderColor = IndexedColors.BLACK.index

            // 设置样式
            cell.cellStyle = cellStyle

            // 设置列宽
            sheet.setColumnWidth(lastCellNum, excel.width * 256)

            // 设置行高
            titleRow.height = (excel.titleHeight * 20).toShort()
        }
    }

    /**
     * 获取所有字段
     *
     * @param clazz 类
     * @return LinkedHashMap
     */
    private fun getFields(clazz: Class<*>): LinkedHashMap<Field, Excel> {
        val fields = listOf(*clazz.declaredFields, *clazz.superclass.declaredFields)

        return fields
            .filter { it.isAnnotationPresent(Excel::class.java) }
            .map { field -> field to AnnotationUtils.getAnnotation(field, Excel::class.java)!! }
            .sortedBy { it.second.sort }
            .associateByTo(LinkedHashMap(), { it.first }, { it.second })
    }

    /**
     * 响应 Excel
     *
     * @param workbook 工作簿
     * @param response 响应
     */
    private fun response(response: HttpServletResponse, workbook: XSSFWorkbook) {
        response.reset()
        response.contentType = ContentType.APPLICATION_XLSX
        response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xlsx")
        workbook.write(response.outputStream)
        response.outputStream.flush()
        response.outputStream.close()
        workbook.close()
    }

}