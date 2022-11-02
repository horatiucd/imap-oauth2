package com.hcd.imapoauth2.mail;

import com.hcd.imapoauth2.config.MailProperties;
import com.hcd.imapoauth2.token.TokenProvider;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class MailReader {

    private final MailProperties mailProperties;
    private final TokenProvider tokenProvider;

    public MailReader(MailProperties mailProperties, TokenProvider tokenProvider) {
        this.mailProperties = mailProperties;
        this.tokenProvider = tokenProvider;
    }

    public Folder getFolder(String name) {
        final Properties props = new Properties();
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.port", 993);
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.imaps.starttls.enable", "true");
        props.put("mail.imaps.auth.mechanisms", "XOAUTH2");

        Session session = Session.getInstance(props);
        try {
            Store store = session.getStore();
            store.connect(mailProperties.getHost(), mailProperties.getUser(), tokenProvider.getAccessToken());
            return store.getFolder(name);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to connect to the default folder.", e);
        }
    }
}
