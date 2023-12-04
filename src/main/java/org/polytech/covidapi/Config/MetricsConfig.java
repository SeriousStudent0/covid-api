package org.polytech.covidapi.Config;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {
    @Bean
    public Counter createAddressCounter() {
        return Counter.build().name("create_address_total").help("Total number of createAddress calls").register();
    }

    @Bean
    public Histogram updateAddressDuration() {
        return Histogram.build().name("update_address_duration_seconds").help("Duration of updateAddress execution").register();
    }
}
