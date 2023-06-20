package com.tang.commons.exception.user;

/**
 * 用户名不唯一异常
 *
 * @author Tang
 */
public class UsernameNotUniqueException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 248759614310466839L;

    public UsernameNotUniqueException() {
    }

    public UsernameNotUniqueException(String message) {
        super(message);
    }

    public UsernameNotUniqueException(Throwable cause) {
        super(cause);
    }

    public UsernameNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

}
