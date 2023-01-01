package com.tang.framework.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tang.commons.exception.ServiceException;
import com.tang.commons.utils.AjaxResult;

/**
 * 全局异常处理
 *
 * @author Tang
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e) {
        LOGGER.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

}
