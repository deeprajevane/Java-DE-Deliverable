package com.practice.GcpLocalPubSub.config;



import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.NoCredentials;
import com.google.cloud.spring.core.GcpProjectIdProvider;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
@Configuration
public class GcpEmulatorConfig {

    @Bean
    public Credentials noCredentials() {
        // Use GoogleCredentials.create() with no credentials for emulator
        return GoogleCredentials.newBuilder().build();
    }

    @Bean
    @Primary
    public Storage storage() {
        return StorageOptions.newBuilder()
                .setHost("http://localhost:4443")
                .setProjectId("dummy-project")
                .setCredentials(NoCredentials.getInstance())
                .build()
                .getService();
    }

    @Bean
    public GcpProjectIdProvider gcpProjectIdProvider() {
        return () -> "dummy-project"; // Same as used in your StorageOptions
    }
}
