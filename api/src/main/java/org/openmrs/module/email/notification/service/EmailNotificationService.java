package org.openmrs.module.email.notification.service;

public interface EmailNotificationService {
    public void sendEmail(String recipientAddress, String subject, String body);
}
