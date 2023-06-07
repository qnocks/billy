package com.qnocks.billy.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Billing Service")
                .version("1.0")
                .description("Subscription Billing system for SaaS businesses")
                .contact(new Contact().name("Roman Ostanin").email("roma.ost2012@mail.ru")));
    }
}
