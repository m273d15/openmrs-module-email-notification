package org.bahmni.module.email.notification.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("emailNotificationService")
public class EmailNotificationService {

    @Autowired
    private EmailNotificationConfig emailNotificationConfig;

    public EmailNotificationService() {}

    public EmailNotificationService(EmailNotificationConfig emailNotificationConfig) {
        this.emailNotificationConfig = emailNotificationConfig;
    }

    /**
     * @param subject         Subject of the email
     * @param body            Body of the email
     * @param to              Email IDs of the recipients
     * @param cc              CC email addresses
     * @param bcc             BCC email addresses
     * @throws EmailException When email ids/body not set
     * @throws EmailException Exception thrown when email is not sent
     */
    public void send(String subject, String body, String[] to, String[] cc, String[] bcc) throws EmailException {
        HtmlEmail htmlEmail = HtmlEmailFactory.getHtmlEmail();
        Properties properties = emailNotificationConfig.getProperties();
        htmlEmail.setFrom(
                properties.getProperty("smtp.from.email.address"),
                properties.getProperty("smtp.from.name")
        );
        htmlEmail.addTo(to);
        if (cc != null) {
            htmlEmail.addCc(cc);
        }
        if (bcc  != null) {
            htmlEmail.addBcc(bcc);
        }
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg(body);
        htmlEmail.setHostName(properties.getProperty("smtp.host"));
        htmlEmail.setAuthentication(
                properties.getProperty("smtp.username"),
                properties.getProperty("smtp.password")
        );
        htmlEmail.setSmtpPort(Integer.parseInt(properties.getProperty("smtp.port")));
        htmlEmail.setSSLOnConnect(Boolean.parseBoolean(properties.getProperty("smtp.ssl")));
        htmlEmail.send();
    }

}