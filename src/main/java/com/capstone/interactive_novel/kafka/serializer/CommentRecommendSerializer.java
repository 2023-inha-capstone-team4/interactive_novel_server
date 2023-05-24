package com.capstone.interactive_novel.kafka.serializer;

import com.capstone.interactive_novel.kafka.message.CommentRecommendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class CommentRecommendSerializer implements Serializer<CommentRecommendMessage> {
    @Override
    public byte[] serialize(String topic, CommentRecommendMessage data) {
        if(data == null) return null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
