package org.bahmni.module.email.notification.service;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("emailNotificationService")
public class EmailNotificationService {
    private PropertiesConfiguration emailConfig;

    public EmailNotificationService() {
    }

    public EmailNotificationService(PropertiesConfiguration emailConfig) {
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
                emailConfig.getString("smtp.from.email.address"),
                emailConfig.getString("smtp.from.name")
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
    public void setEmailConfig(PropertiesConfiguration emailConfig) {
        this.emailConfig = emailConfig;
    }
}
