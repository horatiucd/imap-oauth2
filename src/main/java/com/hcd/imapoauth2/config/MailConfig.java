package com.hcd.imapoauth2.config;

import com.hcd.imapoauth2.mail.MailReader;
import com.hcd.imapoauth2.token.MsalTokenProvider;
import com.hcd.imapoauth2.token.RopcTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Bean
    @Qualifier("msalMailReader")
    public MailReader msalMailReader(MailProperties mailProperties, MsalTokenProvider tokenProvider) {
        return new MailReader(mailProperties, tokenProvider);
    }

    @Bean
    @Qualifier("ropcMailReader")
    public MailReader ropcMailReader(MailProperties mailProperties, RopcTokenProvider tokenProvider) {
        return new MailReader(mailProperties, tokenProvider);
    }
}
