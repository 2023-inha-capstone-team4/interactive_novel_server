package com.capstone.interactive_novel.novel.dto;

import com.capstone.interactive_novel.common.type.PublisherType;
import com.capstone.interactive_novel.novel.type.NovelCategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private List<NovelCategoryType> categoryTypeList;

    public static NovelDto of(Long id, String novelName, String authorName, Long authorId, String novelIntroduce, PublisherType publisherType, String novelImageUrl, long totalScore, List<NovelCategoryType> categoryTypeList) {
        return NovelDto.builder()
                .id(id)
                .novelName(novelName)
                .authorName(authorName)
                .authorId(authorId)
                .novelIntroduce(novelIntroduce)
                .publisherType(publisherType)
                .novelImageUrl(novelImageUrl)
                .totalScore(totalScore)
                .categoryTypeList(categoryTypeList)
                .build();
    }
}
