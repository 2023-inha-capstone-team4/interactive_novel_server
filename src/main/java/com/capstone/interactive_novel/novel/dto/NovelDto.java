package com.capstone.interactive_novel.novel.dto;

import com.capstone.interactive_novel.common.type.PublisherType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NovelDto {
    private long id;
    private String novelName;
    private String authorName;
    private Long authorId;
    private String novelIntroduce;
    private PublisherType publisherType;
    private String novelImageUrl;
    private long totalScore;

    public static NovelDto of(Long id, String novelName, String authorName, Long authorId, String novelIntroduce, PublisherType publisherType, String novelImageUrl, long totalScore) {
        return NovelDto.builder()
                .id(id)
                .novelName(novelName)
                .authorName(authorName)
                .authorId(authorId)
                .novelIntroduce(novelIntroduce)
                .publisherType(publisherType)
                .novelImageUrl(novelImageUrl)
                .totalScore(totalScore)
                .build();
    }
}
