package com.hcd.imapoauth2.token;

import com.hcd.imapoauth2.config.MailProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Qualifier("ropcTokenProvider")
public class RopcTokenProvider implements TokenProvider {

    private final MailProperties mailProperties;
    private final WebClient client;

    public RopcTokenProvider(MailProperties mailProperties) {
        this.mailProperties = mailProperties;

        client = WebClient.builder()
                .baseUrl(mailProperties.getAuthUrl())
                .build();
    }

    @Override
    public String getAccessToken() {
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("client_id", mailProperties.getClientId());
        bodyValues.add("client_secret", mailProperties.getClientSecret());
        bodyValues.add("scope", "https://outlook.office365.com/.default");
        bodyValues.add("grant_type", "client_credentials");

        TokenDTO token = client.post()
                .uri("/{tenantId}/oauth2/v2.0/token",mailProperties.getTenantId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(TokenDTO.class)
                .block();

        if (token == null) {
            throw new RuntimeException("Unable to retrieve OAuth2 access token.");
        }
        return token.getAccessToken();
    }
}
