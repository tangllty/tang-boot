package com.tang.generator.enumeration;

import java.util.Arrays;

/**
 * ORM 类型
 *
 * @author Tang
 */
public enum OrmType {

    MYBATIS("MyBatis"),

    MYBATIS_PLUS("MyBatis-Plus");

    private final String name;

    OrmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OrmType getOrmType(String name) {
        return Arrays.stream(OrmType.values())
            .filter(ormType -> ormType.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

}
