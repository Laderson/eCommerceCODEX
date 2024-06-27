package com.codex.EcommersCodex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.codex.EcommersCodex")
@EnableJpaRepositories(basePackages = "com.codex.EcommersCodex.repository")
public class EcommersCodexApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommersCodexApplication.class, args);
    }
}
