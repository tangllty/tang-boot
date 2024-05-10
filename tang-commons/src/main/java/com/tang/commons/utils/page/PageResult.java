package com.tang.commons.utils.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.tang.commons.constants.HttpStatus;

/**
 * 表格分页数据对象
 *
 * @author Tang
 */
public class PageResult implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 8981940053131272343L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private transient List<?> rows;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 表格数据对象
     */
    public PageResult() {
        this(Collections.emptyList(), 0);
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public PageResult(List<?> list, int total) {
        this(list, (long) total);
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public PageResult(List<?> list, long total) {
        this.rows = list;
        this.total = total;
        this.code = HttpStatus.SUCCESS;
        this.msg = "查询成功";
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
