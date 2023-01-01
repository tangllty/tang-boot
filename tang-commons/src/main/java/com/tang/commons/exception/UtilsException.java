package com.tang.commons.exception;

/**
 * 工具类异常
 *
 * @author Tang
 */
public class UtilsException extends RuntimeException {

    private static final long serialVersionUID = 5639542226042752126L;

    public UtilsException() {
    }

    public UtilsException(String message) {
        super(message);
    }

    public UtilsException(Throwable cause) {
        super(cause);
    }

    public UtilsException(String message, Throwable cause) {
        super(message, cause);
    }

}
