package com.springexamples.kafkaexample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String messageTopic;


    public void publishStringMessage(String message) {
        if (message == null || message.isEmpty()) {
            log.error("message is null");
            return;
        }

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(messageTopic, message);

        future.completable().whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Send message {} to topic {} at partition {}",
                        message,
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition());
            } else {
                log.error("unable to send message {} due to {}",
                        message,
                        ex.getMessage());
            }
        });
    }

    @KafkaListener(topics = "spring-example-topic")
    public void onMessage(
            @Payload String receivedMessage,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String receivedPartitionId) {
        log.info("Received message : {}", receivedMessage);
        log.info("Headers : Topic : {} ", receivedTopic);
        log.info("Headers : PartionId : {} ", receivedPartitionId);

    }
}
