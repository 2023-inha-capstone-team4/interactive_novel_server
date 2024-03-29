package com.capstone.interactive_novel.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.capstone.interactive_novel.kafka.type.KafkaTopicType.COMMENT_RECOMMEND;
import static com.capstone.interactive_novel.kafka.type.KafkaTopicType.NOVEL_REVIEW_SCORE;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic commentRecommendTopic() {
        return TopicBuilder
                .name(COMMENT_RECOMMEND)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic novelReviewScoreTopic() {
        return TopicBuilder
                .name(NOVEL_REVIEW_SCORE)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
