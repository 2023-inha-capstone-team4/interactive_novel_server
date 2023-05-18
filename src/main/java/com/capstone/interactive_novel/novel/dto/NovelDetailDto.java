package com.capstone.interactive_novel.novel.dto;

import com.capstone.interactive_novel.novel.domain.NovelPublisherType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
public class NovelDetailDto {
    private long id;
    private long novelId;
    private String novelDetailName;
    private String novelDetailIntroduce;
    private String authorName;
    @Enumerated(EnumType.STRING)
    private NovelPublisherType novelPublisherType;
    private String novelImageUrl;
    private long totalScore;
    private NovelDetailMediaDto mediaDto;
}
