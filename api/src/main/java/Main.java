import org.openmrs.module.email.notification.service.EmailNotificationService;
import org.openmrs.module.email.notification.service.impl.EmailNotificationServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

        String BODY = String.join(
                System.getProperty("line.separator"),
                "<h1>Amazon SES SMTP Email Test</h1>",
                "<p>This email was sent with Amazon SES using the ",
                "<a href='http://commons.apache.org/proper/commons-email/'>Apache Commons Email</a> Package"
        );

        EmailNotificationService emailService = new EmailNotificationServiceImpl();
        emailService.sendEmail("hamza.kaizar@thoughtworks.com", SUBJECT, BODY);
    }
}
