package com.example.webnovelservice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import com.example.webnovelservice.commons.properties.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableCaching
public class MainApplication {
	@Value("${S3_ACCESSKEY}")
	private String test;

	@PostConstruct
	public void test() {
		System.out.println(">>> " + test);
	}
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
