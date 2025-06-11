# Dockerfile for a Spring Boot service
FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/gcp-employee-112-f1f08708c8a9.json /app/gcp-employee-112-f1f08708c8a9.json
ENTRYPOINT ["java","-jar","/app.jar"]
