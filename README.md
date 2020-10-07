# openmrs-module-email-notification

## Build

```mvn clean package```

The output file is omod/target/email-notification-[VERSION].omod

## Integration Test

At the moment there are no integration tests included into the build, 
but there's a test which is capable of sending out an email. 
Please follow the instructions in ContextTest.java.

## Usage

1. Add a dependency to another Bahmni module:
```
<dependency>
    <groupId>org.bahmni.module</groupId>
    <artifactId>email-notification-omod</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

2. ```EmailNotificationService.send(String subject, String body, String[] to, String[] cc, String[] bcc)```

## Deployment

1. Copy dependencies jars (commons-email-1.5.jar) to /opt/openmrs/openmrs/WEB-INF/lib/ folder.
2. Copy email-notification-[VERSION].omod to /opt/openmrs/modules/
3. Create email-notification.properties file and 
    configure SMTP credentials as in api/src/test/resources/email-notification.properties file.
    Copy this file to TODO.

<sub><sup>No environment variables should contain comma, otherwise they'll be truncated when passed to Java.</sup></sub>

## Supported SMTP server

This module is supposed to be able to work with any SMTP server, but it has only been tested with:
1. Amazon SES
2. Gmail (enable less secure apps)