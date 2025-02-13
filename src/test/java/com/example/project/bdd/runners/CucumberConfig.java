package com.example.project.bdd.runners;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = {CucumberConfig.class})
public class CucumberConfig {
	
	  @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
}
}
