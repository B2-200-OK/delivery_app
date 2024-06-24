package com.sparta.delivery_app.common.exceptionhandler;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalResponse.ErrorResponse;
import com.sparta.delivery_app.common.globalcustomexception.TotalPriceException;
import com.sparta.delivery_app.common.globalcustomexception.global.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "ApiException")
@Order(1)
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Api 요청에 동작 중 예외가 발생한 경우
     */
    @ExceptionHandler(GlobalDuplicatedException.class)
    protected ResponseEntity<ErrorResponse> globalDuplicatedException(GlobalDuplicatedException e) {
        log.error("GlobalDuplicatedException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalNotFoundException.class)
    protected ResponseEntity<ErrorResponse> globalNotFoundException(GlobalNotFoundException e) {
        log.error("GlobalNotFoundException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalAccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> globalAccessDeniedException(GlobalAccessDeniedException e) {
        log.error("GlobalAccessDeniedException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalMismatchException.class)
    protected ResponseEntity<ErrorResponse> globalMismatchException(GlobalMismatchException e) {
        log.error("GlobalMismatchException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalStatusException.class)
    protected ResponseEntity<ErrorResponse> globalStatusException(GlobalStatusException e) {
        log.error("GlobalStatusException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(TotalPriceException.class)
    protected ResponseEntity<ErrorResponse> TotalPriceException(TotalPriceException e) {
        log.error("TotalPriceException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    private static ResponseEntity<ErrorResponse> sendErrorResponse(ErrorCode e) {
        return ResponseEntity.status(e.getHttpStatusCode())
                .body(ErrorResponse.of(e));
    }
}
