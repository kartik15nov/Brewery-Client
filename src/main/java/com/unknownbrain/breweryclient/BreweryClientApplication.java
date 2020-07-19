package com.unknownbrain.breweryclient;

import com.unknownbrain.breweryclient.web.client.BreweryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class BreweryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreweryClientApplication.class, args);
    }

}
