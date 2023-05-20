package com.capstone.interactive_novel.novel.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelReviewInputDto {
    String review;
    int novelScore;
}
