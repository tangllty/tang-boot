package com.tang.commons.utils.page;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tang.commons.constants.HttpStatus;
import com.tang.commons.utils.SqlUtils;

/**
 * 分页工具类
 *
 * @author Tang
 */
public class PageUtils extends PageHelper {

    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage() {
        PageHelper.clearPage();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static TableDataResult getDataTable(List<?> list) {
        return new TableDataResult() {{
            setCode(HttpStatus.SUCCESS);
            setMsg("查询成功");
            setRows(list);
            setTotal(new PageInfo(list).getTotal());
        }};
    }

}
