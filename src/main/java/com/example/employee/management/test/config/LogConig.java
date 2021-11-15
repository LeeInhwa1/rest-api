package com.example.employee.management.test.config;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  AOP 사용 로그 처리를 위한 클래스
 */
@Component
@Aspect
public class LogConig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

//    @Pointcut // 특정

    // 컨트롤러 매핑 뒤에 ..* 혹은 패스 적어서 설정 가능
    @Around("within(com.example.employee.management.test.controller..*)") // AOP 적용을 할 것인지 정의
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {

        String params = getRequestParams();

        long startAt = System.currentTimeMillis();

        log.info("---------------> REQUEST : {}({}) = {}", pjp.getSignature().getDeclaringTypeName(),
                pjp.getSignature().getName(), params);

        Object result = pjp.proceed();

        long endAt = System.currentTimeMillis();

        log.info("---------------> RESPONSE : {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
                pjp.getSignature().getName(), result, endAt - startAt);

        return result;

    }

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), Joiner.on(",").join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }

    // Get request values
    private String getRequestParams() {

        String params = "없음";

        RequestAttributes requestAttributes = RequestContextHolder
                .getRequestAttributes();

        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();

            Map<String, String[]> paramMap = request.getParameterMap();
            if (!paramMap.isEmpty()) {
                params = " [" + paramMapToString(paramMap) + "]";
            }
        }

        return params;

    }

}
