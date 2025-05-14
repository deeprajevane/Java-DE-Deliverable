package com.practice.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class EmailService implements NotificationService {
    protected String serviceName;

    private final static Logger log = LoggerFactory.getLogger(EmailService.class.getName());
    @Override
    public void sendNotification(String recipient,String msg){
        log.info("{} - Sending email to: {}", serviceName, recipient);
        log.info(msg);
    }


}
