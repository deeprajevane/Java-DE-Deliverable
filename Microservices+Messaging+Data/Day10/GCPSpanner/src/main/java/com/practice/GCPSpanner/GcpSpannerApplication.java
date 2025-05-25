package com.practice.GCPSpanner;

import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GcpSpannerApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(GcpSpannerApplication.class);
    private final SpannerTemplate spannerTemplate;

	public GcpSpannerApplication(SpannerTemplate spannerTemplate) {
		this.spannerTemplate = spannerTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(GcpSpannerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Get the Spanner DatabaseClient to test connection
		try {
			spannerTemplate.executeReadOnlyTransaction(txn -> {
				// Simple query to test connection
				txn.executeQuery("SELECT 1");
				return null;
			});
			System.out.println("Successfully connected to Cloud Spanner!");
		} catch (Exception e) {
			System.err.println("Failed to connect to Cloud Spanner:");
			e.printStackTrace();
		}
	}
}
