package com.hcd.imapoauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail")
@Data
public class MailProperties {

    private String host;
    private String user;

    private String authUrl;
    private String clientSecret;
    private String tenantId;
    private String clientId;
}
