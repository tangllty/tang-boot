package com.tang.commons.exception;

/**
 * 验证码异常
 *
 * @author Tang
 */
public class CaptchaException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 7891001344481580446L;

    public CaptchaException() {
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

}
