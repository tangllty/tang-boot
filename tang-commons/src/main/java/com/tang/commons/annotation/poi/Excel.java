package com.tang.commons.annotation.poi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Excel 注解
 *
 * @author Tang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Excel {

    /**
     * Alias for {@link #name}.
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 列名称
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 顺序
     */
    int sort() default 0;

    /**
     * 单元格类型
     */
    CellType cellType() default CellType.STRING;

    /**
     * 日期格式
     */
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 字段类型
     */
    Type type() default Type.ALL;

    /**
     * 单元格类型
     */
    enum CellType {

        /**
         * 字符串
         */
        STRING(0),

        /**
         * 数字
         */
        NUMBER(1),

        /**
         * 日期
         */
        DATE(2);

        private final int code;

        CellType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        /**
         * 根据 code 返回枚举类型
         *
         * @param code 单元格类型
         * @return {@link CellType} 单元格类型
         */
        public static CellType getCellType(int code) {
            for (CellType cellType : CellType.values()) {
                if (cellType.getCode() == code) {
                    return cellType;
                }
            }
            return null;
        }

    }

    /**
     * 字段类型
     */
    enum Type {

        /**
         * 导入导出
         */
        ALL(0),

        /**
        * 导入
        */
        IMPORT(1),

        /**
        * 导出
        */
        EXPORT(2);

        private final int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        /**
        * 根据 code 返回枚举类型
        *
        * @param code 导出或导入
        * @return {@link Type} 导出或导入
        */
        public static Type getType(int code) {
            for (Type type : Type.values()) {
                if (type.getCode() == code) {
                    return type;
                }
            }
            return null;
        }

    }

}
