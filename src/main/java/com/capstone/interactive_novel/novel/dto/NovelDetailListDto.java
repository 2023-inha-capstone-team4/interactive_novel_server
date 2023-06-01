package com.capstone.interactive_novel.novel.dto;

import com.capstone.interactive_novel.common.type.PublisherType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NovelDetailListDto {
    private long id;
    private String novelDetailName;
    private long novelId;
    private String novelName;
    private Long authorId;
    private String authorName;
    private PublisherType publisherType;
    private String novelDetailImageUrl;
}
