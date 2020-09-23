package org.bahmni.module.email.notification.service;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class EmailNotificationConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PropertiesConfiguration emailConfig() throws ConfigurationException {
        return new PropertiesConfiguration("config.properties");
    }

}
