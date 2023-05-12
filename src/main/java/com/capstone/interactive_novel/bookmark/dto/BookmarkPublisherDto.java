package com.capstone.interactive_novel.bookmark.dto;

import com.capstone.interactive_novel.bookmark.domain.BookmarkPublisherEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BookmarkPublisherDto {
    private long id;
    private long targetId;
    private String targetName;
    private String targetImageUrl;

    public static List<BookmarkPublisherDto> createBookmarkPublisherList(List<BookmarkPublisherEntity> bookmarkPublisherList) {
        List<BookmarkPublisherDto> list = new ArrayList<>();
        for (BookmarkPublisherEntity bookmarkPublisherEntity : bookmarkPublisherList) {
            list.add(BookmarkPublisherDto.builder()
                    .id(bookmarkPublisherEntity.getId())
                    .targetId(bookmarkPublisherEntity.getTarget().getId())
                    .targetName(bookmarkPublisherEntity.getTarget().getUsername())
                    .targetImageUrl(bookmarkPublisherEntity.getTarget().getProfileImgUrl())
                    .build());
        }
        return list;
    }
}
