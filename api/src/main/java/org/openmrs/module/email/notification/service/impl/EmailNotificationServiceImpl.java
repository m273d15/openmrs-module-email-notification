package org.openmrs.module.email.notification.service.impl;

import org.openmrs.module.email.notification.service.EmailNotificationService;
import org.springframework.stereotype.Service;

@Service("emailNotificationService")
public class EmailNotificationServiceImpl implements EmailNotificationService {

    public void sendEmail(String recipientAddress, String subject, String body) {
        // TODO
        System.out.println("Sending email...");
    }
}
