package com.tang.commons.utils.poi

import com.google.common.base.CaseFormat
import com.tang.commons.annotation.poi.Excel
import com.tang.commons.annotation.poi.ExcelConfig
import com.tang.commons.constants.ContentType
import com.tang.commons.constants.FileType
import com.tang.commons.enumeration.poi.Type
import com.tang.commons.exception.file.FileTypeMismatchException
import com.tang.commons.kotlin.extensions.width
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
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.CellRangeAddressList
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
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
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
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

    private val fieldsCache: ConcurrentMap<Class<*>, LinkedHashMap<Field, Excel>> = ConcurrentHashMap()

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
        val titleRow = sheet.getRow(if (hasMainTitle(clazz)) 1 else 0)
        val titleIndexMap = getTitleIndex(titleRow)
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
        repeat(rowNum) {
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
            repeat(rowNum) {
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
            setMainTitle(clazz, sheet)
            setTitle(clazz, fields, sheet)
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
            val row = sheet.nextRow()
            getExcelConfig(clazz!!::class.java)?.let {
                row.height = (it.dataHeight.times(20)).toShort()
            }
            fields.forEach { (field: Field, excel: Excel) ->
                val cell = row.createNextCell()
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
     * 设置主标题
     *
     * @param clazz 类
     * @param sheet 工作表
     */
    private fun setMainTitle(clazz: Class<*>, sheet: XSSFSheet) {
        val excelConfig = getExcelConfig(clazz)
        if (excelConfig == null || excelConfig.mainTitle.isEmpty()) {
            return
        }
        val mainTitleRow = sheet.nextRow()
        val mergedCellNum = getFields(clazz).size
        mainTitleRow.height = (excelConfig.mainTitleHeight.times(20)).toShort()
        val cell = mainTitleRow.createCell(0)
        cell.setCellValue(excelConfig.mainTitle)
        cell.cellStyle = getTitleStyle(sheet)
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0, mergedCellNum.minus(1)))
    }

    /**
     * 设置标题
     *
     * @param fields 字段
     * @param sheet  工作表
     */
    private fun setTitle(clazz: Class<*>, fields: LinkedHashMap<Field, Excel>, sheet: XSSFSheet) {
        val titleRow = sheet.nextRow()
        val excelStyle = getExcelConfig(clazz)
        excelStyle?.let { titleRow.height = (it.titleHeight.times(20)).toShort() }
        fields.forEach { (_, excel) ->
            val cell = titleRow.createNextCell()
            cell.setCellValue(excel.name)
            cell.cellStyle = getTitleStyle(sheet)
            val width = if (excel.width == -1) excel.name.width().plus(1) else excel.width
            sheet.setColumnWidth(titleRow.lastCellNum.minus(1), width.times(256))
        }
    }

    /**
     * 获取标题样式
     *
     * @param sheet 工作表
     * @return 样式
     */
    private fun getTitleStyle(sheet: XSSFSheet): XSSFCellStyle {
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
        return cellStyle
    }

    /**
     * 获取所有字段
     *
     * @param clazz 类
     * @return LinkedHashMap
     */
    private fun getFields(clazz: Class<*>): LinkedHashMap<Field, Excel> {
        return fieldsCache.computeIfAbsent(clazz) {
            val fields = listOf(*clazz.declaredFields, *clazz.superclass.declaredFields)
            fields
                .filter { it.isAnnotationPresent(Excel::class.java) }
                .map { field -> field to AnnotationUtils.getAnnotation(field, Excel::class.java)!! }
                .sortedBy { it.second.sort }
                .associateByTo(LinkedHashMap(), { it.first }, { it.second })
        }
    }

    /**
     * 获取 Excel 配置
     *
     * @param clazz 类
     * @return ExcelConfig 配置
     */
    private fun getExcelConfig(clazz: Class<*>): ExcelConfig? {
        return AnnotationUtils.getAnnotation(clazz, ExcelConfig::class.java)
    }

    /**
     * 是否有主标题
     *
     * @param clazz 类
     * @return 是否有主标题
     */
    private fun hasMainTitle(clazz: Class<*>): Boolean {
        return getExcelConfig(clazz)?.mainTitle?.isNotEmpty() == true
    }

    /**
     * 获取下一行
     */
    private fun XSSFSheet.nextRow(): XSSFRow {
        return this.createRow(lastRowNum.plus(1))
    }

    /**
     * 创建下一个单元格
     */
    private fun XSSFRow.createNextCell(): XSSFCell {
        val lastCellNum = if (lastCellNum.toInt() == -1) 0 else lastCellNum.toInt()
        return this.createCell(lastCellNum)
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
