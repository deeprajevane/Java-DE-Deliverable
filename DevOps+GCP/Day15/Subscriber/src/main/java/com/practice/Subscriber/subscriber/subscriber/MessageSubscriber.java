package com.practice.Subscriber.subscriber.subscriber;


import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageSubscriber {

    @Value("${spring.cloud.gcp.pubsub.emulator.host}")
    private String emulatorHost;

    @Value("${spring.cloud.gcp.pubsub.project-id}")
    private String projectId;

    private static final String SUBSCRIPTION_ID = "file-upload-sub";
    private final static Logger log = LoggerFactory.getLogger(MessageSubscriber.class.getName());

    @PostConstruct
    public void subscribe() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(emulatorHost)
                .usePlaintext()
                .build();

        TransportChannelProvider channelProvider = FixedTransportChannelProvider.create(
                GrpcTransportChannel.create(channel)
        );

        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, SUBSCRIPTION_ID);

        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, this::receiveMessage)
                .setChannelProvider(channelProvider)
                .setCredentialsProvider(NoCredentialsProvider.create())
                .build();

        subscriber.startAsync().awaitRunning();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down subscriber...");
            subscriber.stopAsync();
        }));
    }

    private void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        log.info("Received message: {}" , message.getData().toStringUtf8());
        consumer.ack();  // Acknowledge after processing
    }
}
