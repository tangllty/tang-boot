package com.tang.commons.utils;

import java.util.UUID;

/**
 * ID 工具类
 *
 * @author Tang
 */
public class IdUtils {

    private IdUtils() {
    }

    /**
     * 获取 uuid
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取简化 uuid
     * @return uuid
     */
    public static String uuid(boolean simple) {
        if (simple) {
            return uuid().replace("-", "");
        }
        return uuid();
    }

}
