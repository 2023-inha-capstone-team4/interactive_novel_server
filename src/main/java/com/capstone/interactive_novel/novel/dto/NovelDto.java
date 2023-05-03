package com.capstone.interactive_novel.novel.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelDto {
    private long id;
    private String novelName;
    private String novelIntroduce;
    private String publisherType;
    private long publisherId;
    private String publisherName;
    private String NovelImageUrl;
    private long totalScore;
}
