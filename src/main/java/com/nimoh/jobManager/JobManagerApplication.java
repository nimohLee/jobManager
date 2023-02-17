package com.nimoh.jobManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class JobManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(JobManagerApplication.class, args);
	}
}