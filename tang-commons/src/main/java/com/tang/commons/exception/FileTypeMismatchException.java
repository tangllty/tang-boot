package com.tang.commons.exception;

/**
 * 文件类型不匹配异常
 *
 * @author Tang
 */
public class FileTypeMismatchException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 3737376977104929673L;

    public FileTypeMismatchException() {
    }

    public FileTypeMismatchException(String message) {
        super(message);
    }

    public FileTypeMismatchException(Throwable cause) {
        super(cause);
    }

    public FileTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
