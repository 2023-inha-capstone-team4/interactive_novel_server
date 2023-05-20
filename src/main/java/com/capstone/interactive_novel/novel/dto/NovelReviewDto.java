package com.capstone.interactive_novel.novel.dto;

import com.capstone.interactive_novel.common.type.PublisherType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NovelReviewDto {
    private long id;
    private String reviewerName;
    private Long reviewerId;
    private String review;
    private Long novelId;
    private int novelScore;
    private PublisherType publisherType;

    public static NovelReviewDto of(long id, String reviewerName, Long reviewerId, String review, Long novelId, int novelScore, PublisherType publisherType) {
        return NovelReviewDto.builder()
                .id(id)
                .reviewerName(reviewerName)
                .reviewerId(reviewerId)
                .review(review)
                .novelId(novelId)
                .novelScore(novelScore)
                .publisherType(publisherType)
                .build();
    }
}
