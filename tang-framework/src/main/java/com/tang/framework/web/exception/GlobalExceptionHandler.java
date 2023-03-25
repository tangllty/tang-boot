package com.tang.framework.web.exception;

import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tang.commons.constants.HttpStatus;
import com.tang.commons.exception.ServiceException;
import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.LogUtils;

/**
 * 全局异常处理
 *
 * @author Tang
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e) {
        LOGGER.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e) {
        LOGGER.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e) {
        LOGGER.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.FORBIDDEN, "权限受限，请联系管理员授权");
    }

}
