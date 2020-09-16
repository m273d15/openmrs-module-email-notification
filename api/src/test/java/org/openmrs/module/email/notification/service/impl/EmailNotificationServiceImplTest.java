package org.openmrs.module.email.notification.service.impl;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.openmrs.module.email.notification.service.EmailNotificationService;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmailNotificationServiceImplTest {

    private EmailNotificationService emailNotificationService;

    @Mock
    HtmlEmail htmlEmail;

    @Mock
    PropertiesConfiguration emailConfig;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        initMocks(this);
        emailNotificationService = new EmailNotificationServiceImpl(htmlEmail, emailConfig);
    }

    @Test
    public void shouldSendEmail() throws Exception {
        emailNotificationService.sendEmail("test@gmail.com", "Test subject", "Test body");
        verify(htmlEmail, times(1)).send();
    }

    @Test
    public void shouldThrowEmailExceptionIfSMPTCredentialsAreNotConfigured() throws Exception {
        expectedException.expect(EmailException.class);
        when(htmlEmail.send()).thenThrow(EmailException.class);
        emailNotificationService.sendEmail("test@gmail.com", "Test subject", "Test body");
    }
}