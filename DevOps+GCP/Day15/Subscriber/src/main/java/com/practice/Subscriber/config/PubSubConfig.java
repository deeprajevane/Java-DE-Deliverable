package com.practice.Subscriber.config;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubConfig {

    @Value("${spring.cloud.gcp.pubsub.emulator.host}")
    private String emulatorHost;

    @Value("${spring.cloud.gcp.pubsub.project-id}")
    private String projectId;

    @Bean
    public TransportChannelProvider channelProvider() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(emulatorHost)
                .usePlaintext()
                .build();

        return FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel));
    }

    @Bean
    public NoCredentialsProvider credentialsProvider() {
        return NoCredentialsProvider.create();
    }
}
