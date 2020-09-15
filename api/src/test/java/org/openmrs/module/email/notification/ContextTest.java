package org.openmrs.module.email.notification;

import org.junit.Test;
import org.openmrs.module.email.notification.service.EmailNotificationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class ContextTest extends BaseModuleContextSensitiveTest {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Test
    public void ensureEmailNotificationServiceIsAvailableInTheContext() {
        assertNotNull(emailNotificationService);
    }

    @Test
    public void sendEmail() throws Exception {
        String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

        String BODY = String.join(
                System.getProperty("line.separator"),
                "<h1>Amazon SES SMTP Email Test</h1>",
                "<p>This email was sent with Amazon SES using the ",
                "<a href='http://commons.apache.org/proper/commons-email/'>Apache Commons Email</a> Package"
        );

//        emailNotificationService.sendEmail("kbondar@thoughtworks.com", SUBJECT, BODY);
        emailNotificationService.sendEmail("hamza.kaizar@thoughtworks.com", SUBJECT, BODY);
    }
}
