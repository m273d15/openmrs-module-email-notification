package org.openmrs.module.email.notification.service.impl;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.openmrs.module.email.notification.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service("emailNotificationService")
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private HtmlEmail htmlEmail;

    public EmailNotificationServiceImpl() {}
    public EmailNotificationServiceImpl(HtmlEmail htmlEmail) {
        this.htmlEmail = htmlEmail;
    }

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

    public void sendEmail(String recipientAddress, String subject, String body) throws EmailException {
        Properties emailConfig = loadConfig();

        try {
            htmlEmail.setHostName(emailConfig.getProperty("host"));
            htmlEmail.setAuthentication(
                    System.getenv("AWS_SMTP_USERNAME"),
                    System.getenv("AWS_SMTP_PASSWORD")
            );
            htmlEmail.setSmtpPort(Integer.parseInt(emailConfig.getProperty("port")));
            htmlEmail.setSSLOnConnect(true);
            htmlEmail.addTo(recipientAddress);
            htmlEmail.setFrom(emailConfig.getProperty("from"), emailConfig.getProperty("fromName"));
            htmlEmail.setSubject(subject);
            htmlEmail.setHtmlMsg(body);
            htmlEmail.send();
        } catch (EmailException ex) {
            System.out.println(ex);
        }
    }

    @Autowired
    public void setHtmlEmail(HtmlEmail htmlEmail) {
        this.htmlEmail = htmlEmail;
    }

    // TODO: move to another class
    @Bean
    public HtmlEmail htmlEmail() {
        return new HtmlEmail();
    }
}
