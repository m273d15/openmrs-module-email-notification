# openmrs-module-email-notification

## Modify Settings
- Insert the following snippet in `~/.m2/settings.xml`
```xml
<servers>
...
    <server>
      <id>bahmni-email-notification</id>
      <username>${iam-user-access-key-id}</username>
      <password>${iam-user-secret-key}</password>
    </server>
...
</servers>
```
- Set the region to Mumbai
```shell
export AWS_DEFAULT_REGION=ap-south-1
export AWS_REGION=ap-south-1
```

## Build & Deploy

```shell
mvn --settings ~/.m2/settings.xml \
clean package deploy \
-Diam-user-access-key-id=<ACCESS_KEY> \
-Diam-user-secret-key=<SECURITY_KEY>
```

## Integration Test

At the moment there are no integration tests included into the build, 
but there's a test which is capable of sending out an email. 
Please follow the instructions in ContextTest.java.

## Usage

- Add the following dependencies to `pom.xml` in another Bahmni module:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.bahmni.module</groupId>
            <artifactId>email-notification-omod</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<build>
    <extensions>
        <extension>
            <groupId>com.github.platform-team</groupId>
            <artifactId>aws-maven</artifactId>
            <version>6.0.0</version>
        </extension>
    </extensions>
</build>

<repositories>
    <repository>
        <id>email-notification</id>
        <name>Email Notification</name>
        <url>https://bahmni-email-notification.s3.ap-south-1.amazonaws.com/snapshot/</url>
    </repository>
</repositories>
```
- Function to send email:
```java
EmailNotificationService.send(
    String subject, 
    String body, 
    String[] to, 
    String[] cc, 
    String[] bcc
)
```

## Deployment

1. Copy dependencies jar (commons-email-1.5.jar) to `/opt/openmrs/openmrs/WEB-INF/lib/`
2. Create email-notification.properties file and 
    - configure SMTP credentials as in api/src/test/resources/email-notification.properties file.
    - This file should be located at /opt/openmrs/email-notification.properties.

## Supported SMTP server

This module should work with any SMTP server, but it has only been tested with:
1. Amazon SES
2. Gmail (enable less secure apps)
