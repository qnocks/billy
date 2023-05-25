package com.qnocks.billy.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableJpaAuditing
@EnableScheduling
public class BillyServiceConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
