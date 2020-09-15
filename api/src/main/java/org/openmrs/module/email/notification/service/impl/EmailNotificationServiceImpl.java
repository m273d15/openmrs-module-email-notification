package org.openmrs.module.email.notification.service.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.openmrs.module.email.notification.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service("emailNotificationService")
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private HtmlEmail htmlEmail;

    public EmailNotificationServiceImpl() {}
    public EmailNotificationServiceImpl(HtmlEmail htmlEmail) {
        this.htmlEmail = htmlEmail;
    }

    public void sendEmail(String recipientAddress, String subject, String body) throws EmailException, ConfigurationException {
        PropertiesConfiguration emailConfig = new PropertiesConfiguration("config.properties");

        htmlEmail.setHostName(emailConfig.getString("HOST"));
        htmlEmail.setAuthentication(
                emailConfig.getString("SMTP_USERNAME"),
                emailConfig.getString("SMTP_PASSWORD")
        );
        htmlEmail.setSmtpPort(emailConfig.getInt("PORT"));
        htmlEmail.setSSLOnConnect(true);
        htmlEmail.addTo(recipientAddress);
        htmlEmail.setFrom(emailConfig.getString("FROM"), emailConfig.getString("FROM_NAME"));
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg(body);
        htmlEmail.send();
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
