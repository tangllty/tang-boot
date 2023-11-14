package com.tang.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 字典数据权限
 *
 * @author Tang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictPermission {

    /**
     * Alias for {@link #name}.
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 字典名称
     */
    @AliasFor("value")
    String name() default "";

}
