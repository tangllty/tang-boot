package com.tang.extension.mybatisplus.utils

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.tang.commons.utils.page.PageResult
import com.tang.commons.utils.page.PageSupport

/**
 * MyBatis Plus 分页工具类
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
    fun <T> page(baseMapper: BaseMapper<T>, queryWrapper: QueryWrapper<T>): PageResult {
        val pageDomain = PageSupport.buildPageRequest()
        queryWrapper.orderBy(pageDomain.orderBy.isNotBlank(), pageDomain.isAscBoolean, pageDomain.orderByColumn)
        val page = Page<T>(pageDomain.pageNum.toLong(), pageDomain.pageSize.toLong())
        val selectPage = baseMapper.selectPage(page, queryWrapper)
        return PageResult(selectPage.records, selectPage.total)
    }

}
