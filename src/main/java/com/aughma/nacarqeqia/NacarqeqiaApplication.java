package com.aughma.nacarqeqia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class NacarqeqiaApplication {

    public static void main(String[] args) {
        // Note: We're handling properties encoding in WebConfig, 
        // so we don't need to set system properties here
        System.setProperty("spring.messages.encoding", StandardCharsets.UTF_8.name());
        
        SpringApplication.run(NacarqeqiaApplication.class, args);
    }

}
