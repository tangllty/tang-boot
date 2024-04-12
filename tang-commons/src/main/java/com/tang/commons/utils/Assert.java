package com.tang.commons.utils;

import java.util.Objects;
import java.util.function.Supplier;

import com.tang.commons.exception.AssertException;

/**
 * 断言工具类，符合条件时抛出异常
 *
 * @author Tang
 */
public class Assert {

    private Assert() {
    }

    /**
     * 表达式为 True 时抛出异常
     *
     * @param expression 表达式
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "表达式为 True");
    }

    /**
     * 表达式为 True 时抛出异常
     *
     * @param expression 表达式
     * @param message    异常信息
     */
    public static void isTrue(boolean expression, String message) {
        isTrue(expression, new AssertException(message));
    }

    /**
     * 表达式为 True 时抛出异常
     *
     * @param expression 表达式
     * @param message    异常信息
     * @param args       异常信息参数
     *
     * @see StringUtils#format(String, Object...)
     */
    public static void isTrue(boolean expression, String message, Object... args) {
        isTrue(expression, new AssertException(StringUtils.format(message, args)));
    }

    /**
     * 表达式为 True 时抛出异常
     *
     * @param expression 表达式
     * @param cause      异常
     */
    public static void isTrue(boolean expression, RuntimeException cause) {
        isTrue(expression, () -> cause);
    }

    /**
     * 表达式为 True 时抛出异常
     *
     * @param expression 表达式
     * @param cause      异常
     */
    public static void isTrue(boolean expression, Supplier<? extends RuntimeException> cause) {
        if (expression) {
            throw cause.get();
        }
    }

    /**
     * 表达式为 False 时抛出异常
     *
     * @param expression 表达式
     */
    public static void isFalse(boolean expression) {
        isFalse(expression, "表达式为 False");
    }

    /**
     * 表达式为 False 时抛出异常
     *
     * @param expression 表达式
     * @param message    异常信息
     */
    public static void isFalse(boolean expression, String message) {
        isFalse(expression, new AssertException(message));
    }

    /**
     * 表达式为 False 时抛出异常
     *
     * @param expression 表达式
     * @param message    异常信息
     * @param args       异常信息参数
     *
     * @see StringUtils#format(String, Object...)
     */
    public static void isFalse(boolean expression, String message, Object... args) {
        isFalse(expression, new AssertException(StringUtils.format(message, args)));
    }

    /**
     * 表达式为 False 时抛出异常
     *
     * @param expression 表达式
     * @param cause      异常
     */
    public static void isFalse(boolean expression, RuntimeException cause) {
        isFalse(expression, () -> cause);
    }

    /**
     * 表达式为 False 时抛出异常
     *
     * @param expression 表达式
     * @param cause      异常
     */
    public static void isFalse(boolean expression, Supplier<? extends RuntimeException> cause) {
        if (!expression) {
            throw cause.get();
        }
    }

    /**
     * 对象为 Null 时抛出异常
     *
     * @param object 对象
     */
    public static void isNull(Object object) {
        isNull(object, "对象为 Null");
    }

    /**
     * 对象为 Null 时抛出异常
     *
     * @param object  对象
     * @param message 异常信息
     */
    public static void isNull(Object object, String message) {
        isNull(object, new AssertException(message));
    }

    /**
     * 对象为 Null 时抛出异常
     *
     * @param object 对象
     * @param message 异常信息
     * @param args    异常信息参数
     *
     * @see StringUtils#format(String, Object...)
     */
    public static void isNull(Object object, String message, Object... args) {
        isNull(object, new AssertException(StringUtils.format(message, args)));
    }

    /**
     * 对象为 Null 时抛出异常
     *
     * @param object 对象
     * @param cause  异常
     */
    public static void isNull(Object object, RuntimeException cause) {
        isNull(object, () -> cause);
    }

    /**
     * 对象为 Null 时抛出异常
     *
     * @param object 对象
     * @param cause  异常
     */
    public static void isNull(Object object, Supplier<? extends RuntimeException> cause) {
        if (Objects.isNull(object)) {
            throw cause.get();
        }
    }

    /**
     * 对象不为 Null 时抛出异常
     *
     * @param object 对象
     */
    public static void nonNull(Object object) {
        nonNull(object, "对象不为 Null");
    }

    /**
     * 对象不为 Null 时抛出异常
     *
     * @param object  对象
     * @param message 异常信息
     */
    public static void nonNull(Object object, String message) {
        nonNull(object, new AssertException(message));
    }

    /**
     * 对象不为 Null 时抛出异常
     *
     * @param object 对象
     * @param message 异常信息
     * @param args    异常信息参数
     *
     * @see StringUtils#format(String, Object...)
     */
    public static void nonNull(Object object, String message, Object... args) {
        nonNull(object, new AssertException(StringUtils.format(message, args)));
    }

    /**
     * 对象不为 Null 时抛出异常
     *
     * @param object 对象
     * @param cause  异常
     */
    public static void nonNull(Object object, RuntimeException cause) {
        nonNull(object, () -> cause);
    }

    /**
     * 对象不为 Null 时抛出异常
     *
     * @param object 对象
     * @param cause  异常
     */
    public static void nonNull(Object object, Supplier<? extends RuntimeException> cause) {
        if (Objects.nonNull(object)) {
            throw cause.get();
        }
    }

}
