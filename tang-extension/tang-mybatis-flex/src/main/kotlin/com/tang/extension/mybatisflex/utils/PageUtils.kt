package com.tang.extension.mybatisflex.utils

import com.mybatisflex.core.BaseMapper
import com.mybatisflex.core.query.QueryWrapper
import com.tang.commons.utils.page.PageResult
import com.tang.commons.utils.page.PageSupport

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
    fun <T> page(baseMapper: BaseMapper<T>, queryWrapper: QueryWrapper): PageResult {
        val pageDomain = PageSupport.buildPageRequest()
        if (pageDomain.orderBy.isNotBlank()) {
            queryWrapper.orderBy(pageDomain.orderByColumn, pageDomain.isAscBoolean)
        }
        val paginate = baseMapper.paginate(pageDomain.pageNum, pageDomain.pageSize, queryWrapper)
        return PageResult(paginate.records, paginate.totalRow)
    }

}
