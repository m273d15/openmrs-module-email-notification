package org.bahmni.module.email.notification.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Properties;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyVararg;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HtmlEmailFactory.class})
public class EmailNotificationServiceTest {

    private EmailNotificationService emailNotificationService;

    @Mock
    HtmlEmail htmlEmail;

    @Mock
    Properties emailConfig;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        initMocks(this);
        mockStatic(HtmlEmailFactory.class);
        PowerMockito.when(HtmlEmailFactory.getHtmlEmail()).thenReturn(htmlEmail);
        emailNotificationService = new EmailNotificationService(emailConfig);
    }

    @Test
    public void shouldSendEmail() throws Exception {
        when(emailConfig.getProperty("smtp.from.email.address")).thenReturn("someFromAddress");
        when(emailConfig.getProperty("smtp.from.name")).thenReturn("someFromName");
        when(emailConfig.getProperty("smtp.host")).thenReturn("someHost");
        when(emailConfig.getProperty("smtp.username")).thenReturn("someUser");
        when(emailConfig.getProperty("smtp.password")).thenReturn("somePassword");
        when(emailConfig.getProperty("smtp.port")).thenReturn("123");
        when(emailConfig.getProperty("smtp.ssl")).thenReturn("TRUE");
        emailNotificationService.send("Test subject",
                "Test body",
                new String[]{ "test@gmail.com", "test2@gmail.com" },
                null,
                null
                );
        verify(htmlEmail).setFrom(eq("someFromAddress"), eq("someFromName"));
        verify(htmlEmail).addTo((String[])anyVararg());
        verify(htmlEmail, times(0)).addCc((String[])anyVararg());
        verify(htmlEmail, times(0)).addBcc((String[])anyVararg());
        verify(htmlEmail).setSubject(eq("Test subject"));
        verify(htmlEmail).setHtmlMsg(eq("Test body"));
        verify(htmlEmail).setHostName(eq("someHost"));
        verify(htmlEmail).setAuthentication(eq("someUser"), eq("somePassword"));
        verify(htmlEmail).setSmtpPort(eq(123));
        verify(htmlEmail).setSSLOnConnect(eq(true));
        verify(htmlEmail, times(1)).send();
        verifyStatic();
    }

    @Test
    public void shouldSendEmailWithCcAndBcc() throws Exception {
        when(emailConfig.getProperty(anyString())).thenReturn("123");
        emailNotificationService.send("Test subject",
                "Test body",
                new String[]{ "test@gmail.com", "test2@gmail.com" },
                new String[]{ "cc@gmail.com" },
                new String[]{ "bcc@gmail.com" }
        );
        verify(htmlEmail).addCc((String[])anyVararg());
        verify(htmlEmail).addBcc((String[])anyVararg());
        verify(htmlEmail, times(1)).send();
        verifyStatic();
    }

    @Test
    public void shouldThrowEmailExceptionIfSMPTCredentialsAreNotConfigured() throws Exception {
        when(emailConfig.getProperty(anyString())).thenReturn("123");
        expectedException.expect(EmailException.class);
        BDDMockito.given(htmlEmail.send()).willThrow(new EmailException());
        emailNotificationService.send("Test subject",
                "Test body",
                new String[]{ "test@gmail.com" },
                null,
                null
        );
    }

}