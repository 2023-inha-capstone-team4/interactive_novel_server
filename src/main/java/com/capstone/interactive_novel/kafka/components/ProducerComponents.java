package com.capstone.interactive_novel.kafka.components;

import com.capstone.interactive_novel.kafka.message.CommentRecommendMessage;
import com.capstone.interactive_novel.kafka.message.NovelReviewScoreMessage;
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
    private final KafkaTemplate<String, CommentRecommendMessage> commentRecommendKafkaTemplate;
    private final KafkaTemplate<String, NovelReviewScoreMessage> novelReviewScoreKafkaTemplate;

    public void sendCommentRecommendMessage(String topic, CommentRecommendMessage message) {
        logger.info("sending payload = '{}' to topic = '{}'", message, topic);
        ListenableFuture<SendResult<String, CommentRecommendMessage>> listenable = commentRecommendKafkaTemplate.send(topic, message);
    }

    public void sendNovelReviewScoreMessage(String topic, NovelReviewScoreMessage message) {
        logger.info("sending payload = '{}' to topic = '{}'", message, topic);
        ListenableFuture<SendResult<String, NovelReviewScoreMessage>> listenable = novelReviewScoreKafkaTemplate.send(topic, message);
    }
}
