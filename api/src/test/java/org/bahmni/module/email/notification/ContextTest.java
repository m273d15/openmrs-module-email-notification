package org.bahmni.module.email.notification;

import org.junit.Ignore;
import org.junit.Test;
import org.bahmni.module.email.notification.service.EmailNotificationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

@Ignore
public class ContextTest extends BaseModuleContextSensitiveTest {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Test
    public void ensureEmailNotificationServiceIsAvailableInTheContext() {
        assertNotNull(emailNotificationService);
    }

    /**
     * To send a test email
     * 1) Remove @Ignore annotation below
     * 2) Configure environment variables, see config.properties
     * 3) Change "To" email address to yours
     * 4) If sending via Amazon SES in sandbox, ensure your email address is added to verified email addresses
     */

    @Test
    public void sendEmail() throws Exception {
        String SUBJECT = "Email test from openmrs-module-email-notification";

        String BODY = "This is a test email";

        emailNotificationService.send(SUBJECT, BODY, new String[]{"hamza.kaizar@thoughtworks.com"}, null, null);
    }
}
