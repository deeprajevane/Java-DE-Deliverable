package com.practice.GcpLocalPubSub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		com.google.cloud.spring.autoconfigure.storage.GcpStorageAutoConfiguration.class
})
public class GcpLocalPubSubApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcpLocalPubSubApplication.class, args);
	}

}
