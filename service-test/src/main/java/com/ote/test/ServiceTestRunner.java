package com.ote.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
