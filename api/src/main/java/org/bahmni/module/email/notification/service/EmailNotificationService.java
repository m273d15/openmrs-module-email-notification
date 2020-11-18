package org.bahmni.module.email.notification.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.bahmni.module.email.notification.EmailNotificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
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
    public void send(String subject, String body, String[] to, String[] cc, String[] bcc, String logo) throws EmailNotificationException {
        try {
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
            if (bcc != null) {
                htmlEmail.addBcc(bcc);
            }
            htmlEmail.setSubject(subject);
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body,"text/html");
            multipart.addBodyPart(messageBodyPart);
            if(logo != null) {
                DataSource fds = new FileDataSource(
                        logo);

                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", "<image>");
                multipart.addBodyPart(messageBodyPart);
            }

            htmlEmail.setContent(multipart);

            //htmlEmail.setHtmlMsg(body);
            htmlEmail.setHostName(properties.getProperty("smtp.host"));
            htmlEmail.setAuthentication(
                    properties.getProperty("smtp.username"),
                    properties.getProperty("smtp.password")
            );
            htmlEmail.setSmtpPort(Integer.parseInt(properties.getProperty("smtp.port")));
            htmlEmail.setSSLOnConnect(Boolean.parseBoolean(properties.getProperty("smtp.ssl")));
            htmlEmail.send();
        } catch (IOException e) {
            throw new EmailNotificationException("Unable to load email-notification.properties, see details in README", e);
        } catch (EmailException e) {
            throw new EmailNotificationException("SMTP credentials are invalid", e);
        } catch (MessagingException e){

        }
    }

}