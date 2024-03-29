package com.qnocks.billy.core.exception.handler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final String message;
    private final Integer status;
    private final HttpStatus error;
    private String stackTrace;
    private final Long timestamp;
}
