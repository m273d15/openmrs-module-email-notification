package org.bahmni.module.email.notification.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Component("emailNotificationConfig")
public class EmailNotificationConfig {
    protected Log log = LogFactory.getLog(getClass());

    private Properties properties;

    private void loadProperties() {
        try {
            String resourceName = "email-notification.properties";
            final File file = new File(OpenmrsUtil.getApplicationDataDirectory(), resourceName);
            final InputStream inputStream;
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
            }
            properties.load(inputStream);
        } catch (Exception e) {
            log.error("Failed to load email-notification.properties. Sending email notifications is not possible.");
        }
    }

    public Properties getProperties() {
        if (properties == null) {
             properties = new Properties();
             loadProperties();
        }
        return properties;
    }
}
