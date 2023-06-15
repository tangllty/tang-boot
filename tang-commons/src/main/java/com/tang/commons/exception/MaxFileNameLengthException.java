package com.tang.commons.exception;

/**
 * 文件名过长异常
 *
 * @author Tang
 */
public class MaxFileNameLengthException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 4599632146806006591L;

    public MaxFileNameLengthException() {
    }

    public MaxFileNameLengthException(String message) {
        super(message);
    }

    public MaxFileNameLengthException(Throwable cause) {
        super(cause);
    }

    public MaxFileNameLengthException(String message, Throwable cause) {
        super(message, cause);
    }

}
