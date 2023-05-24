package com.capstone.interactive_novel.kafka.config;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.kafka.message.CommentRecommendMessage;
import com.capstone.interactive_novel.kafka.serializer.CommentRecommendDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, CommentRecommendMessage> commentRecommendConsumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CommentRecommendDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CommentRecommendMessage> commentRecommendKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, CommentRecommendMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(commentRecommendConsumerFactory());
        factory.setCommonErrorHandler(customErrorHandler());
        return factory;
    }

    @Bean
    public DefaultErrorHandler customErrorHandler() {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(new FixedBackOff(0L, 2L));
        errorHandler.addNotRetryableExceptions(INovelException.class);

        return errorHandler;
    }
}
