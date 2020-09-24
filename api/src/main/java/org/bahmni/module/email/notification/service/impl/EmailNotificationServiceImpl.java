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

    public EmailNotificationServiceImpl() {}
    public EmailNotificationServiceImpl(HtmlEmail htmlEmail, PropertiesConfiguration emailConfig) {
        this.htmlEmail = htmlEmail;
        this.emailConfig = emailConfig;
    }

    /**
     * @param     recipientAddress Email address of the recipient
     * @param     subject          Subject of the email
     * @param     body             Body of the email
     * @exception EmailException   Exception thrown when email is not sent
     */
    public void sendEmail(String recipientAddress, String subject, String body) throws EmailException {
        htmlEmail.setHostName(emailConfig.getString("smtp.host"));
        htmlEmail.setAuthentication(
                emailConfig.getString("smtp.username"),
                emailConfig.getString("smtp.password")
        );
        htmlEmail.setSmtpPort(emailConfig.getInt("smtp.port"));
        htmlEmail.setSSLOnConnect(emailConfig.getBoolean("smtp.ssl"));
        htmlEmail.addTo(recipientAddress);
        htmlEmail.setFrom(emailConfig.getString("smtp.from.email.address"), emailConfig.getString("smtp.from.name"));
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg(body);
        htmlEmail.send();
    }

    @Autowired
    public void setHtmlEmail(HtmlEmail htmlEmail) {
        this.htmlEmail = htmlEmail;
    }

    @Autowired
    public  void setEmailConfig(PropertiesConfiguration emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Bean
    public HtmlEmail htmlEmail() {
        return new HtmlEmail();
    }
}
