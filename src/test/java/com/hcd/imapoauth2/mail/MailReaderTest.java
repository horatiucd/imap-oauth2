package com.hcd.imapoauth2.mail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.Folder;

@SpringBootTest
class MailReaderTest {

    @Autowired
    @Qualifier("msalMailReader")
    private MailReader msalMailReader;

    @Autowired
    @Qualifier("ropcMailReader")
    private MailReader ropcMailReader;

    private void getFolder(MailReader mailReader) {
        final String name = "INBOX";
        Folder folder = mailReader.getFolder("INBOX");
        Assertions.assertNotNull(folder);
        Assertions.assertEquals(name, folder.getFullName());
    }

    @Test
    void getFolderMsal() {
        getFolder(msalMailReader);
    }

    @Test
    void getFolderRopc() {
        getFolder(ropcMailReader);
    }
}
