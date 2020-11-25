package org.bahmni.module.email.notification.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;

import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceFileResolver;
import org.bahmni.module.email.notification.EmailNotificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Service("emailNotificationService")
public class EmailNotificationService {

    @Autowired
    private EmailNotificationConfig emailNotificationConfig;

    private Log log = LogFactory.getLog(this.getClass());

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
    public void send(String subject, String body, String[] to, String[] cc, String[] bcc, String logo) throws EmailNotificationException {
        try {
            Properties properties = emailNotificationConfig.getProperties();
            String htmlEmailTemplate = body;
            log.warn(htmlEmailTemplate);
            if(logo != null) {
                htmlEmailTemplate += "<img src=" + "\"" + logo + "\"" + ">";
            }
            log.warn(htmlEmailTemplate);
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceFileResolver(new File("/home/bahmni/patient_images/"), true));
            email.setHostName(properties.getProperty("smtp.host"));
            email.addTo("ippilimahesh1999@gmail.com");
            if (cc != null) {
                email.addCc(cc);
            }
            if (bcc != null) {
                email.addBcc(bcc);
            }
            email.setFrom(
                    properties.getProperty("smtp.from.email.address"),
                    properties.getProperty("smtp.from.name")
            );
            email.setSubject(subject);
            email.setHtmlMsg(htmlEmailTemplate);
            email.setTextMsg("Your email client does not support HTML messages");
            email.setAuthentication(
                    properties.getProperty("smtp.username"),
                    properties.getProperty("smtp.password")
            );
            email.setSmtpPort(Integer.parseInt(properties.getProperty("smtp.port")));
            email.setSSLOnConnect(Boolean.parseBoolean(properties.getProperty("smtp.ssl")));
            email.send();
        } catch (IOException e) {
            throw new EmailNotificationException("Unable to load email-notification.properties, see details in README", e);
        } catch (EmailException e) {
            throw new EmailNotificationException("SMTP credentials are invalid", e);
        }
    }

}