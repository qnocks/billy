package com.qnocks.billy.core.exception.custom;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
public class TenantException extends RuntimeException {

    @Builder.Default
    private final String message = "Error occurred while tenant processing";

    @Builder.Default
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
}