package com.tang.commons.exception;

/**
 * 断言异常
 *
 * @author Tang
 */
public class AssertException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 1953209867997857028L;

    public AssertException() {
    }

    public AssertException(String message) {
        super(message);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

}
