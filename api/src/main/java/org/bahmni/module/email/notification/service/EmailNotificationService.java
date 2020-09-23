package org.bahmni.module.email.notification.service;

import org.apache.commons.mail.EmailException;

public interface EmailNotificationService {
    void sendEmail(String recipientAddress, String subject, String body) throws EmailException;
}
