package cn.queue.common.exception;
import cn.queue.common.domain.CommonResult;
import cn.queue.common.exception.define.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Larry
 * @description: 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *
     * @param bizException 业务异常
     * @return 异常信息处理通用返回
     */
    @ExceptionHandler(BizException.class)
    public CommonResult<?> handleBizException(BizException bizException) {
        log.warn("# 业务异常:{}", bizException.getMessage(), bizException);
        return CommonResult.fail(bizException.getMessage());
    }

    /**
     * @param e 异常信息
     * @return 通用异常处理
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<?> validationBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" && "));
        log.error("# 参数错误: {}", message, e);
        return CommonResult.fail(message);
    }
}
