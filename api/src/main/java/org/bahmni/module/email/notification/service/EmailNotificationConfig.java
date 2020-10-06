package org.bahmni.module.email.notification.service;

import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class EmailNotificationConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Properties emailConfig() throws IOException {
        String propertyFile = new File(OpenmrsUtil.getApplicationDataDirectory(), "email-notification.properties").getAbsolutePath();
        Properties properties = new Properties(System.getProperties());
        properties.load(new FileInputStream(propertyFile));
        return properties;
    }

}
