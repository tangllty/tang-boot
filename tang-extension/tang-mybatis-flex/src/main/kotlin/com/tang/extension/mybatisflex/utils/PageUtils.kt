package com.tang.extension.mybatisflex.utils

import com.mybatisflex.core.BaseMapper
import com.mybatisflex.core.query.QueryWrapper
import com.tang.commons.constants.HttpStatus
import com.tang.commons.utils.page.TableDataResult
import com.tang.commons.utils.page.TableSupport

/**
 * MyBatis Flex 分页工具类
 *
 * @author Tang
 */
object PageUtils {

    /**
     * 分页查询
     *
     * @param baseMapper   通用 Mapper 接口
     * @param queryWrapper 查询条件
     * @param <T>          实体类
     * @return 分页结果
     */
    @JvmStatic
    fun <T> page(baseMapper: BaseMapper<T>, queryWrapper: QueryWrapper): TableDataResult {
        val pageDomain = TableSupport.buildPageRequest()
        val paginate = baseMapper.paginate(pageDomain.pageNum, pageDomain.pageSize, queryWrapper)
        val tableDataResult = TableDataResult()
        tableDataResult.code = HttpStatus.SUCCESS
        tableDataResult.msg = "查询成功"
        tableDataResult.rows = paginate.records
        tableDataResult.total = paginate.totalRow
        return tableDataResult
    }

}
