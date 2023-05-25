package com.capstone.interactive_novel.kafka.serializer;

import com.capstone.interactive_novel.kafka.message.NovelReviewScoreMessage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class NovelReviewScoreDeserializer implements Deserializer<NovelReviewScoreMessage> {
    @Override
    public NovelReviewScoreMessage deserialize(String topic, byte[] data) {
        if(data == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(data, NovelReviewScoreMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
