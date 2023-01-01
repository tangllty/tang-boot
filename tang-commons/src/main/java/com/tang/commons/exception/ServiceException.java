package com.tang.commons.exception;

/**
 * 业务异常
 *
 * @author Tang
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 4087812489526530941L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
