package org.bahmni.module.email.notification.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("emailNotificationService")
public class EmailNotificationService {

    private Properties emailConfig;

    public EmailNotificationService() {
    }

    public EmailNotificationService(Properties emailConfig) {
        this.emailConfig = emailConfig;
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
        htmlEmail.setFrom(
                emailConfig.getProperty("smtp.from.email.address"),
                emailConfig.getProperty("smtp.from.name")
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
        htmlEmail.setHostName(emailConfig.getProperty("smtp.host"));
        htmlEmail.setAuthentication(
                emailConfig.getProperty("smtp.username"),
                emailConfig.getProperty("smtp.password")
        );
        htmlEmail.setSmtpPort(Integer.parseInt(emailConfig.getProperty("smtp.port")));
        htmlEmail.setSSLOnConnect(Boolean.parseBoolean(emailConfig.getProperty("smtp.ssl")));
        htmlEmail.send();
    }

    @Autowired
    public void setEmailConfig(Properties emailConfig) {
        this.emailConfig = emailConfig;
    }
}