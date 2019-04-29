package com.twitter.twitterapp.configuration;

import com.twitter.twitterapp.exception.TAResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new TAResponseErrorHandler());
        return restTemplate;
    }
}
