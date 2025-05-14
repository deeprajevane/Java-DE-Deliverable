package com.java.practice;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            log.info("i = {}", i);
        }
    }


}