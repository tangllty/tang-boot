package com.tang.commons.exception.file;

/**
 * 文件不存在异常
 *
 * @author Tang
 */
public class FileNotExistException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = -8106083046775314368L;

    public FileNotExistException() {
    }

    public FileNotExistException(String message) {
        super(message);
    }

    public FileNotExistException(Throwable cause) {
        super(cause);
    }

    public FileNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
