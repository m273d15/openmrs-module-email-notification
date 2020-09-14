package org.openmrs.module.email.notification.service.impl;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.openmrs.module.email.notification.service.EmailNotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service("emailNotificationService")
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private Properties loadConfig() {
        String propFileName = "config.properties";
        Properties emailConfig = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        try {
            emailConfig.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emailConfig;
    }

    public void sendEmail(String recipientAddress, String subject, String body) {
        Properties emailConfig = loadConfig();

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(emailConfig.getProperty("host"));
            email.setAuthentication(
                    System.getenv("AWS_SMTP_USERNAME"),
                    System.getenv("AWS_SMTP_PASSWORD")
            );
            email.setSmtpPort(Integer.parseInt(emailConfig.getProperty("port")));
            email.setSSLOnConnect(true);
            email.addTo(recipientAddress);
            email.setFrom(emailConfig.getProperty("from"), emailConfig.getProperty("fromName"));
            email.setSubject(subject);
            email.setHtmlMsg(body);
            email.send();
        } catch (EmailException ex) {
            System.out.println(ex);
        }
    }
}
