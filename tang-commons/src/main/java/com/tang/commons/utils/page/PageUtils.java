package com.tang.commons.utils.page;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.tang.commons.constants.HttpStatus;
import com.tang.commons.utils.SqlUtils;

/**
 * 分页工具类
 *
 * @author Tang
 */
public class PageUtils extends PageHelper {

    private PageUtils() {
    }

    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        var pageDomain = PageSupport.buildPageRequest();
        var pageNum = pageDomain.getPageNum();
        var pageSize = pageDomain.getPageSize();
        var orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
        var reasonable = pageDomain.getReasonable();
        PageMethod.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage() {
        PageMethod.clearPage();
    }

    /**
     * 响应请求分页数据
     */
    public static PageResult getDataTable(List<?> list) {
        var tableDataResult = new PageResult();
        tableDataResult.setCode(HttpStatus.SUCCESS);
        tableDataResult.setMsg("查询成功");
        tableDataResult.setRows(list);
        tableDataResult.setTotal(new PageInfo<>(list).getTotal());
        return tableDataResult;
    }

}
