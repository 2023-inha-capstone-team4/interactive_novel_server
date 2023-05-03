package com.capstone.interactive_novel.novel.dto;

import com.capstone.interactive_novel.novel.domain.NovelPublisherType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelDto {
    private long id;
    private String novelName;
    private String novelIntroduce;
    private NovelPublisherType publisherType;
    private long publisherId;
    private String publisherName;
    private String NovelImageUrl;
    private long totalScore;
}
