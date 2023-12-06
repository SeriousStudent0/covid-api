package org.polytech.covidapi.Metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@Aspect
public class AddressMetricsAspect {

    private final MeterRegistry registry;

    @Autowired
    public AddressMetricsAspect(MeterRegistry registry) {
        this.registry = registry;
    }

    // Success counter of the executed method among all from AddressService
    @AfterReturning("execution(public * org.polytech.covidapi.Services.AddressService.*(..))")
    public void successExecCount(JoinPoint joinPoint) {
        String tag = joinPoint.getSignature().getName();
        registry.counter(tag + "_exec_success_count", tag).increment();
    }

    // Fail counter of the executed method among all from AddressService
    @AfterThrowing("execution(public * org.polytech.covidapi.Services.AddressService.*(..))")
    public void failExecCount(JoinPoint joinPoint) {
        String tag = joinPoint.getSignature().getName();
        registry.counter(tag + "_exec_fail_count", tag).increment();
    }

    // Execution duration of the executed method among all from AddressService
    @Around("execution(public * org.polytech.covidapi.Services.AddressService.*(..))")
    public Object duration(ProceedingJoinPoint joinPoint)
            throws Throwable {
        String tag = joinPoint.getSignature().getName();
        Timer timer = registry.timer(tag + "_exec_time", tag);
        Instant startTime = Instant.now();
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            timer.record(Duration.between(startTime, Instant.now()));
        }   
    }
}
