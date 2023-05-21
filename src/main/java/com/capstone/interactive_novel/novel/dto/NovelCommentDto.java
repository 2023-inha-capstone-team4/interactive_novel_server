package com.capstone.interactive_novel.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NovelCommentDto {
    Long id;
    Long readerId;
    String readerName;
    Long recommendAmount;
    String comment;

    public static NovelCommentDto of(Long id, Long readerId, String readerName, String comment) {
        return NovelCommentDto.builder()
                .id(id)
                .readerId(readerId)
                .readerName(readerName)
                .comment(comment)
                .build();
    }
}
