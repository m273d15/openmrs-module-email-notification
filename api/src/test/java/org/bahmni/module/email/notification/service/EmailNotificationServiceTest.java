package org.bahmni.module.email.notification.service;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmailNotificationServiceTest {

    private EmailNotificationService emailNotificationService;

    @Mock
    HtmlEmail htmlEmail;

    @Mock
    PropertiesConfiguration emailConfig;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws EmailException {
        initMocks(this);
        emailNotificationService = new EmailNotificationService(htmlEmail, emailConfig);
    }

    @Test
    public void shouldSendEmail() throws Exception {
        emailNotificationService.create("Test subject", "Test body", "test@gmail.com")
                .addCc("test@gmail.com")
                .addBcc("test@gmail.com")
                .send();
        verify(htmlEmail, times(1)).send();
    }

    @Test
    public void shouldThrowEmailExceptionIfSMPTCredentialsAreNotConfigured() throws Exception {
        expectedException.expect(EmailException.class);
        when(htmlEmail.send()).thenThrow(EmailException.class);
        emailNotificationService.create("Test subject", "Test body", "test@gmail.com")
                .addCc("test@gmail.com")
                .addBcc("test@gmail.com")
                .send();
    }
}