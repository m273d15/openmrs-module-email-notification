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

    public void sendEmail(String recipientAddress, String subject, String body) throws IOException {
        String propFileName = "config.properties";
        Properties emailConfig = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        emailConfig.load(inputStream);
        inputStream.close();

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(emailConfig.getProperty("host"));
            email.setAuthentication(emailConfig.getProperty("smtp_username"), emailConfig.getProperty("smtp_password"));
            email.setSmtpPort(Integer.parseInt(emailConfig.getProperty("port")));
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
