package com.practice;

import com.practice.controller.NotificationController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class SpringFramework {
    private final static Logger log = LoggerFactory.getLogger(SpringFramework.class.getName());
    public static void main(String[] args) {


        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        NotificationController controller = context.getBean(NotificationController.class);

        log.info("Sending email notification:");
        controller.sendEmailNotification();

        context.close();
    }
}