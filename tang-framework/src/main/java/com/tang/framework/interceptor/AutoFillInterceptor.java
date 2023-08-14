package com.tang.framework.interceptor;

import java.time.LocalDateTime;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import com.tang.commons.utils.SecurityUtils;

/**
 * 自动填充拦截器
 *
 * @author Tang
 */
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
public class AutoFillInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        var invocationArgs = invocation.getArgs();
        var mappedStatement = (MappedStatement) invocationArgs[0];
        var invocationObject = invocationArgs[1];
        var sqlCommandType = mappedStatement.getSqlCommandType();

        switch (sqlCommandType) {
            case INSERT -> {
                var clazz = invocationObject.getClass();
                clazz.getMethod("setCreateBy", String.class).invoke(invocationObject, SecurityUtils.getUser().getUsername());
                clazz.getMethod("setCreateTime", LocalDateTime.class).invoke(invocationObject, LocalDateTime.now());
            }
            case UPDATE -> {
                var clazz = invocationObject.getClass();
                clazz.getMethod("setUpdateBy", String.class).invoke(invocationObject, SecurityUtils.getUser().getUsername());
                clazz.getMethod("setUpdateTime", LocalDateTime.class).invoke(invocationObject, LocalDateTime.now());
            }
            default -> {
            }
        }

        return invocation.proceed();
    }

}
