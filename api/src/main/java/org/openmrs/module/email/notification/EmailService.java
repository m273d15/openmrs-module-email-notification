package org.openmrs.module.email.notification;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    public static void sendEmail(String recipientAddress, String subject, String body) throws Exception {

        Properties emailConfig = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = null;

        try {
            inputStream = EmailService.class.getClassLoader().getResourceAsStream(propFileName);
            emailConfig.load(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }

        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", emailConfig.getProperty("port"));
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailConfig.getProperty("from"), emailConfig.getProperty("fromName")));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));
        msg.setSubject(subject);
        msg.setContent(body, "text/html");

        Transport transport = session.getTransport();

        try {
            transport.connect(emailConfig.getProperty("host"), emailConfig.getProperty("smtp_username"), emailConfig.getProperty("smtp_password"));
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        } finally {
            transport.close();
        }
    }
}