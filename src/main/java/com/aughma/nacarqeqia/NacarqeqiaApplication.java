package com.aughma.nacarqeqia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class NacarqeqiaApplication {

    public static void main(String[] args) {
        System.setProperty("spring.messages.encoding", StandardCharsets.UTF_8.name());
        
        SpringApplication.run(NacarqeqiaApplication.class, args);
    }

}
