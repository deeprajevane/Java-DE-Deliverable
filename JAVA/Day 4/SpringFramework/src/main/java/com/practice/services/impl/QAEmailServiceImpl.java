package com.practice.services.impl;

import com.practice.services.EmailService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Service
@Profile("QA")
public class QAEmailServiceImpl extends EmailService {
    private final static Logger log = LoggerFactory.getLogger(QAEmailServiceImpl.class.getName());
    public QAEmailServiceImpl(@Value("${email.service.name:QA Email service}") String serviceName){
        this.serviceName= serviceName;
    }

    @Override
    public void sendNotification(String recipients,String msg){
        log.info("[QA mode] would send an email");
        log.info("{} sending a message: {}",recipients,msg);
    }



}
