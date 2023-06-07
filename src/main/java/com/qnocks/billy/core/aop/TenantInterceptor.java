package com.qnocks.billy.core.aop;

import com.qnocks.billy.core.exception.custom.TenantException;
import com.qnocks.billy.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Aspect
@Component
@RequiredArgsConstructor
public class TenantInterceptor {

    private final TenantRepository tenantRepository;

    // TODO: for now this feature with TenantContext is useless,
    //  because I pass tenantId to service layer with specified repository methods
    @Around("@annotation(com.qnocks.billy.core.aop.TenantRelatedRequest)")
    public Object logInboundRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        var tenantId = extractTenantId(joinPoint).orElseThrow(() -> TenantException.builder()
                .message("cannot find Tenant with provided id")
                .status(HttpStatus.NOT_FOUND)
                .build());

        var tenant = tenantRepository.findById(tenantId).orElseThrow(RuntimeException::new);

        TenantContext.setCurrentTenant(tenant);

        try {
            return joinPoint.proceed();
        } finally {
            TenantContext.clear();
        }
    }

    public Optional<String> extractTenantId(ProceedingJoinPoint joinPoint) {
        var signature = joinPoint.getSignature();
        var method = ((MethodSignature) signature).getMethod();
        var annotationMatrix = method.getParameterAnnotations();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            var annotations = annotationMatrix[i];
            for (var annotation : annotations) {
                if (annotation.annotationType().equals(TenantId.class)) {
                    return of((String) joinPoint.getArgs()[i]);
                }
            }
        }
        return empty();
    }
}
