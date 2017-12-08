package com.ote.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServiceTestRunner {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(ServiceTestRunner.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
       return new RestTemplate();
    }

}
