package com.practice.GcpLocalPubSub.config;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.TopicName;
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
        return InstantiatingGrpcChannelProvider.newBuilder()
                .setEndpoint(emulatorHost)
                .setChannelConfigurator(managedChannelBuilder -> {
                    managedChannelBuilder.usePlaintext();
                    return managedChannelBuilder;
                })
                .build();
    }

    @Bean
    public Publisher publisher() throws Exception {
        TopicName topicName = TopicName.of(projectId, "file-upload-topic");
        return Publisher.newBuilder(topicName)
                .setChannelProvider(channelProvider())
                .setCredentialsProvider(NoCredentialsProvider.create())
                .build();
    }

    @Bean
    public TopicAdminClient topicAdminClient() throws Exception {
        return TopicAdminClient.create(
                TopicAdminSettings.newBuilder()
                        .setTransportChannelProvider(channelProvider())
                        .setCredentialsProvider(NoCredentialsProvider.create())
                        .build()
        );
    }

    @Bean
    public SubscriptionAdminClient subscriptionAdminClient() throws Exception {
        return SubscriptionAdminClient.create(
                SubscriptionAdminSettings.newBuilder()
                        .setTransportChannelProvider(channelProvider())
                        .setCredentialsProvider(NoCredentialsProvider.create())
                        .build()
        );
    }
}
