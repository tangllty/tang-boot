package com.tang.commons.exception.user;

/**
 * 邮箱不唯一异常
 *
 * @author Tang
 */
public class EmailNotUniqueException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 572352418983152223L;

    public EmailNotUniqueException() {
    }

    public EmailNotUniqueException(String message) {
        super(message);
    }

    public EmailNotUniqueException(Throwable cause) {
        super(cause);
    }

    public EmailNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

}
