package org.openmrs.module.email.notification.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openmrs.api.context.Context;
import org.openmrs.module.email.notification.service.EmailNotificationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;

public class EmailNotificationServiceImplTest extends BaseModuleContextSensitiveTest {

    @Autowired
    private EmailNotificationService emailNotificationService;

    // Mock something to verify an email was sent

    @Test
    public void shouldSendEmail() {
        emailNotificationService.sendEmail("test@gmail.com", "Test subject", "Test body");
        // TODO: add expectations
    }
}