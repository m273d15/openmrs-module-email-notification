package org.openmrs.module.email.notification.service;

import java.io.IOException;

public interface EmailNotificationService {
    void sendEmail(String recipientAddress, String subject, String body) throws IOException;
}
