package com.hcd.imapoauth2.token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    @Qualifier("msalTokenProvider")
    private TokenProvider msalTokenProvider;

    @Autowired
    @Qualifier("ropcTokenProvider")
    private TokenProvider ropcTokenProvider;

    private void getAccessToken(TokenProvider tokenProvider) {
        final String token = tokenProvider.getAccessToken();
        Assertions.assertNotNull(token);
    }

    @Test
    void getAccessTokenMsal() {
        getAccessToken(msalTokenProvider);
    }

    @Test
    void getAccessTokenRopc() {
        getAccessToken(ropcTokenProvider);
    }
}
