package org.bahmni.module.email.notification;

import org.junit.Ignore;
import org.junit.Test;
import org.bahmni.module.email.notification.service.EmailNotificationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertNotNull;

@Ignore
@TestPropertySource("classpath:email-notification.properties")
public class ContextTest extends BaseModuleContextSensitiveTest {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Test
    public void ensureEmailNotificationServiceIsAvailableInTheContext() {
        assertNotNull(emailNotificationService);
    }

    /**
     * To send a test email
     * 1) Remove @Ignore annotation above
     * 2) Configure SMTP credentials in src/test/resources/email-notification.properties
     * 3) Change "To" email address to yours
     */

    @Test
    public void sendEmail() throws Exception {
        String SUBJECT = "Email test from openmrs-module-email-notification";

        String BODY = "This is a test email";

        emailNotificationService.send(SUBJECT, BODY, new String[]{"someemail@gmail.com"}, null, null);
    }
}
