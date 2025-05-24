package com.aughma.nacarqeqia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource("classpath:myconfig.properties")
@Getter
public class ExternalConfigProperties {

    @Value("${custom.message}")
    private String customMessage;
    
    @Value("${custom.environment}")
    private String environment;
    
    @Value("${custom.feature.enabled}")
    private boolean featureEnabled;
    
    @Value("${custom.max-items}")
    private int maxItems;
}
