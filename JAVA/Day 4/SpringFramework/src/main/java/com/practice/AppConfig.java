package com.practice;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = "com.practice")
@PropertySource("classpath:application.properties")
@PropertySource(value = "classpath:application.${spring.profiles.active}.properties", ignoreResourceNotFound = true)
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Profile("dev")
    public String devProfileMessage() {
        return "Development profile is active";
    }

    @Bean
    @Profile("QA")
    public String QAProfileMessage() {
        return "QA profile is active";
    }
}