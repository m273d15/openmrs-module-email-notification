package org.openmrs.module.email.notification.service;

import java.io.IOException;

public interface EmailNotificationService {
    public void sendEmail(String recipientAddress, String subject, String body) throws IOException;
}
