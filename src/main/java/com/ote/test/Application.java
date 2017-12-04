package com.ote.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private SampleService service;

    // Given a sample user is memory
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() throws Exception {

        // NO: if defined after, authorities will replace roles.
        //return new InMemoryUserDetailsManager(Arrays.asList(User.withUsername("steve.jobs").password("password").roles("USER").authorities("FOO_READ_PRIVILEGE").build()));

        // Assuming I have received the following userRight for my application
        return new InMemoryUserDetailsManager(
                Arrays.asList(User.
                        withUsername("olivier.terrien").
                        password("ght19kc;").
                        authorities("FOO_READ_PRIVILEGE", "ROLE_USER", "DEAL_READ", "DEAL/GLE_WRITE").build()));
    }

    @Override
    public void run(String... args) throws Exception {

        // Assuming user steve.jobs is connected
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("olivier.terrien", "ght19kc;"));

        try {
            log.warn(this.service.secureForGlencore());
        } finally {
            SecurityContextHolder.clearContext();
        }
        System.exit(0);
    }

}