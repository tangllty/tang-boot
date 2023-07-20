package com.tang.commons.utils.id;

import java.util.UUID;

/**
 * ID 工具类
 *
 * @author Tang
 */
public class IdUtils {

    private IdUtils() {
    }

    private static final Snowflake SNOWFLAKE = new Snowflake(1, 1);

    /**
     * 获取 UUID
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取简化 UUID
     * @return UUID
     */
    public static String uuid(boolean simple) {
        if (simple) {
            return uuid().replace("-", "");
        }
        return uuid();
    }

    /**
     * 获取雪花算法 ID
     *
     * @return ID
     */
    public static long snowflake() {
        return SNOWFLAKE.nextId();
    }

}
