package com.capstone.interactive_novel.kafka.components;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.kafka.message.CommentRecommendMessage;
import com.capstone.interactive_novel.kafka.message.NovelReviewScoreMessage;
import com.capstone.interactive_novel.novel.service.NovelCommentService;
import com.capstone.interactive_novel.novel.service.NovelReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.capstone.interactive_novel.kafka.type.KafkaTopicType.COMMENT_RECOMMEND;
import static com.capstone.interactive_novel.kafka.type.KafkaTopicType.NOVEL_REVIEW_SCORE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerComponents {
    private final NovelCommentService novelCommentService;
    private final NovelReviewService novelReviewService;

    @KafkaListener(topics = COMMENT_RECOMMEND, groupId = "inovel", containerFactory = "commentRecommendKafkaListener")
    public void receiveCommentRecommendMessage(CommentRecommendMessage message, ConsumerRecord<String, String> record) {
        log.info("Message Received.");
        try {
            novelCommentService.processRecommendCommentMessage(message.getReaderId(), message.getCommentId());
        } catch (INovelException e) {
            log.error("Cannot Process Message: #" + record.offset());
            log.error("INovelException: " + e.getErrorMessage());
        }
    }

    @KafkaListener(topics = NOVEL_REVIEW_SCORE, groupId = "inovel", containerFactory = "novelReviewScoreKafkaListener")
    public void receiveNovelReviewScoreMessage(NovelReviewScoreMessage message, ConsumerRecord<String, String> record) {
        log.info("Message Received.");
        try {
            novelReviewService.processNovelReviewScoreMessage(message.getNovelId(), message.getReviewScore(), message.getReviewerCount());
        } catch (INovelException e) {
            log.error("Cannot Process Message: #" + record.offset());
            log.error("INovelException: " + e.getErrorMessage());
        }
    }
}
