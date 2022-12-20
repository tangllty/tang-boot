package com.tang.commons.utils.page;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

/**
 * 分页数据
 *
 * @author Tang
 */
public class PageDomain {

    private static final String ASCENDING = "ascending";

    private static final String DESCENDING = "descending";

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    private String isAsc = "asc";

    /**
     * 分页参数合理化
     */
    private Boolean reasonable = true;

    public String getOrderBy() {
        if (StringUtils.isBlank(orderByColumn)) {
            return "";
        }
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, orderByColumn) + " " + isAsc;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        if (StringUtils.isNoneBlank(isAsc)) {
            if (ASCENDING.equals(isAsc)) {
                isAsc = "asc";
            }
            if (DESCENDING.equals(isAsc)) {
                isAsc = "desc";
            }
            this.isAsc = isAsc;
        }
    }

    public Boolean getReasonable() {
        if (reasonable == null) {
            return Boolean.TRUE;
        }
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }

}
