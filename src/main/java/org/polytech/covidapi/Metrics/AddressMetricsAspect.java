package org.polytech.covidapi.Metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
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

    private final Counter counterCall;
    private final Counter counterFail;
    private final Histogram timer;
    private final Counter createAddressCounter;
    private final Histogram updateAddressDuration;

    @Autowired
    public AddressMetricsAspect(
            Counter counterCall, Counter counterFail, Histogram timer,
            Counter createAddressCounter, Histogram updateAddressDuration
    ) {
        this.counterCall = counterCall;
        this.counterFail = counterFail;
        this.timer = timer;
        this.createAddressCounter = createAddressCounter;
        this.updateAddressDuration = updateAddressDuration;
    }

    @AfterThrowing("execution(public * org.polytech.covidapi.Services.AddressService.*(..))")
    public void afterThrowingAddressService(JoinPoint joinPoint) {
        counterFail.inc();
    }

    @Around("execution(public * org.polytech.covidapi.Services.AddressService.createAddress(..))")
    public Object aroundCreateAddress(ProceedingJoinPoint joinPoint) throws Throwable {
        createAddressCounter.inc();
        return measureDuration(joinPoint, timer);
    }

    @Around("execution(public * org.polytech.covidapi.Services.AddressService.updateAddress(..))")
    public Object aroundUpdateAddress(ProceedingJoinPoint joinPoint) throws Throwable {
        return measureDuration(joinPoint, updateAddressDuration);
    }

    private Object measureDuration(ProceedingJoinPoint joinPoint, Histogram histogram) throws Throwable {
        Histogram.Timer timer = histogram.startTimer();
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            timer.observeDuration();
        }
    }
}
