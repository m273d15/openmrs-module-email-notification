package org.bahmni.module.email.notification.service.impl;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.bahmni.module.email.notification.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service("emailNotificationService")
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private HtmlEmail htmlEmail;
    private PropertiesConfiguration emailConfig;

    public EmailNotificationServiceImpl() {
    }

    public EmailNotificationServiceImpl(HtmlEmail htmlEmail, PropertiesConfiguration emailConfig) throws EmailException {
        this.htmlEmail = htmlEmail;
        this.emailConfig = emailConfig;
    }

    /**
     * @param subject         Subject of the email
     * @param body            Body of the email
     * @param emailIds        Email IDs of the recipients (to)
     * @throws EmailException When email ids/body not set
     */
    @Override
    public EmailNotificationServiceImpl create(String subject, String body, String... emailIds) throws EmailException {
        htmlEmail.setFrom(
                emailConfig.getString("smtp.from.email.address"),
                emailConfig.getString("smtp.from.name")
        );
        htmlEmail.addTo(emailIds);
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg(body);
        return this;
    }

    /**
     * @param emailIds        Emails of the recipients (cc)
     * @throws EmailException When email ids not added
     */
    @Override
    public EmailNotificationServiceImpl addCc(String... emailIds) throws EmailException {
        htmlEmail.addCc(emailIds);
        return this;
    }

    /**
     * @param emailIds        Emails of the recipients (bcc)
     * @throws EmailException When emailI ids not added
     */
    @Override
    public EmailNotificationServiceImpl addBcc(String... emailIds) throws EmailException {
        htmlEmail.addBcc(emailIds);
        return this;
    }

    /**
     * @throws EmailException Exception thrown when email is not sent
     */
    @Override
    public void send() throws EmailException {
        htmlEmail.setHostName(emailConfig.getString("smtp.host"));
        htmlEmail.setAuthentication(
                emailConfig.getString("smtp.username"),
                emailConfig.getString("smtp.password")
        );
        htmlEmail.setSmtpPort(emailConfig.getInt("smtp.port"));
        htmlEmail.setSSLOnConnect(emailConfig.getBoolean("smtp.ssl"));
        htmlEmail.send();
    }

    @Autowired
    public void setHtmlEmail(HtmlEmail htmlEmail) {
        this.htmlEmail = htmlEmail;
    }

    @Autowired
    public void setEmailConfig(PropertiesConfiguration emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Bean
    public HtmlEmail htmlEmail() {
        return new HtmlEmail();
    }
}
