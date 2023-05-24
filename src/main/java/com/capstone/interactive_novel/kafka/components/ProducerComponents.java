package com.capstone.interactive_novel.kafka.components;

import com.capstone.interactive_novel.kafka.message.CommentRecommendMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@RequiredArgsConstructor
public class ProducerComponents {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, CommentRecommendMessage> kafkaTemplate;

    public void sendCommentRecommendMessage(String topic, CommentRecommendMessage message) {
        logger.info("sending payload = '{}' to topic = '{}'", message, topic);
        ListenableFuture<SendResult<String, CommentRecommendMessage>> listenable = kafkaTemplate.send(topic, message);
    }
}
