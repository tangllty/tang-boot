package com.tang.commons.exception;

/**
 * 工具类异常
 *
 * @author Tang
 */
public class UtilsException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    public UtilsException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilsException(String message) {
        super(message);
    }

    public UtilsException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
