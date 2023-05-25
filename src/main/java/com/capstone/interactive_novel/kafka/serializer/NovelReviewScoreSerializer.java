package com.capstone.interactive_novel.kafka.serializer;

import com.capstone.interactive_novel.kafka.message.NovelReviewScoreMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class NovelReviewScoreSerializer implements Serializer<NovelReviewScoreMessage> {
    @Override
    public byte[] serialize(String topic, NovelReviewScoreMessage data) {
        if(data == null) return null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
