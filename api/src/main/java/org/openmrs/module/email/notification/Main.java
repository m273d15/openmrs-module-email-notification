package org.openmrs.module.email.notification;

public class Main {
    public static void main(String[] args) throws Exception {
        String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

        String BODY = String.join(
                System.getProperty("line.separator"),
                "<h1>Amazon SES SMTP Email Test</h1>",
                "<p>This email was sent with Amazon SES using the ",
                "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
                " for <a href='https://www.java.com'>Java</a>."
        );

        EmailService.sendEmail("hamza.kaizar@thoughtworks.com", SUBJECT, BODY);
    }
}
