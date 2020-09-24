package org.bahmni.module.email.notification.service;

import org.apache.commons.mail.EmailException;
import org.bahmni.module.email.notification.service.impl.EmailNotificationServiceImpl;

public interface EmailNotificationService {
    EmailNotificationServiceImpl create(String subject, String body, String... emailIds) throws EmailException;
    EmailNotificationServiceImpl addCc(String... recipientEmails) throws EmailException;
    EmailNotificationServiceImpl addBcc(String... recipientEmails) throws EmailException;
    void send() throws EmailException;
}
