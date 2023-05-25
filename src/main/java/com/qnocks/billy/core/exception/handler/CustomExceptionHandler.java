package com.qnocks.billy.core.exception.handler;

import com.qnocks.billy.core.exception.custom.PaymentException;
import com.qnocks.billy.core.exception.custom.ProductException;
import com.qnocks.billy.core.exception.custom.SubscriptionException;
import com.qnocks.billy.core.exception.custom.TenantException;
import com.qnocks.billy.core.util.DateTimeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(TenantException.class)
    public ResponseEntity<ErrorResponse> handleTenantException(TenantException e) {
        return buildResponse(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorResponse> handleProductException(ProductException e) {
        return buildResponse(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(SubscriptionException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionException(SubscriptionException e) {
        return buildResponse(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(PaymentException e) {
        return buildResponse(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception e) {
        // TODO: change when debug is done
//        return buildResponse("Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        return buildResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status) {
        var response = ErrorResponse.builder()
                .message(message)
                .status(status.value())
                .error(status)
                .timestamp(DateTimeUtils.toSeconds(LocalDateTime.now()))
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
