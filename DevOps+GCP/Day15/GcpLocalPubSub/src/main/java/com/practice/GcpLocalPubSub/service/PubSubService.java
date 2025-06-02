package com.practice.GcpLocalPubSub.service;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubSubService {

    private final Publisher publisher;
    private final static Logger log = LoggerFactory.getLogger(PubSubService.class.getName());

    @Autowired
    public PubSubService(Publisher publisher) {
        this.publisher = publisher;
    }

    public void publishMessage(String message) {
        try {
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(ByteString.copyFromUtf8(message))
                    .build();
            publisher.publish(pubsubMessage).get();
            log.info("Message published: {} ",message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish message", e);
        }
    }
}