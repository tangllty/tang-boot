package com.tang.extension.mybatisflex.query;

import com.mybatisflex.core.query.FunctionQueryColumn;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.util.LambdaGetter;
import com.mybatisflex.core.util.LambdaUtil;
import com.tang.extension.mybatisflex.constants.FuncName;

/**
 * 自定义查询函数
 *
 * @author Tang
 */
public class QueryFunctions {

    private QueryFunctions() {
    }

    public static QueryColumn toPinyin(String columnX) {
        return new FunctionQueryColumn(FuncName.TO_PINYIN, columnX);
    }

    public static QueryColumn toPinyin(QueryColumn columnX) {
        return new FunctionQueryColumn(FuncName.TO_PINYIN, columnX);
    }

    public static <T> QueryColumn toPinyin(LambdaGetter<T> columnX) {
        return new FunctionQueryColumn(FuncName.TO_PINYIN, LambdaUtil.getQueryColumn(columnX));
    }

}
