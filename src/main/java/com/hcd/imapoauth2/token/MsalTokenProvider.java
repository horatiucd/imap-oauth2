package com.hcd.imapoauth2.token;

import com.hcd.imapoauth2.config.MailProperties;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IClientCredential;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.Set;

@Component
@Qualifier("msalTokenProvider")
public class MsalTokenProvider implements TokenProvider {

    private final ConfidentialClientApplication confidentialClientApp;
    private final Set<String> scopes;

    public MsalTokenProvider(MailProperties mailProperties) throws MalformedURLException {
        IClientCredential credential = ClientCredentialFactory.createFromSecret(mailProperties.getClientSecret());

        final String authority = String.format("%s/%s",
                mailProperties.getAuthUrl(), mailProperties.getTenantId());
        confidentialClientApp = ConfidentialClientApplication.builder(mailProperties.getClientId(), credential)
                .authority(authority)
                .build();

        scopes = Set.of("https://outlook.office365.com/.default");
    }

    @Override
    public String getAccessToken() {
        try {
            ClientCredentialParameters parameters = ClientCredentialParameters
                    .builder(scopes)
                    .build();

            return confidentialClientApp.acquireToken(parameters)
                    .join()
                    .accessToken();
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve OAuth2 access token.", e);
        }
    }
}
