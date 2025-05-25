package com.practice.GCPSpanner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.cloud.gcp")
public class GcpProperties {

    private String projectId;
    private String instanceId;
    private String database;
    private Credentials credentials = new Credentials();

    public static class Credentials {
        private String location;

        public String getLocation() {
            return location;
        }
        public void setLocation(String location) {
            this.location = location;
        }
    }

    // Getters and setters
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getInstanceId() {
        return instanceId;
    }
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
    public String getDatabase() {
        return database;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public Credentials getCredentials() {
        return credentials;
    }
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}

