package com.capstone.interactive_novel.bookmark.dto;

import com.capstone.interactive_novel.bookmark.domain.BookmarkNovelEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BookmarkNovelDto {
    private long id;
    private long novelId;
    private String novelName;
    private String novelImageUrl;

    public static List<BookmarkNovelDto> createBookmarkNovelList(List<BookmarkNovelEntity> bookmarkNovelList) {
        List<BookmarkNovelDto> list = new ArrayList<>();
        for (BookmarkNovelEntity bookmarkNovelEntity : bookmarkNovelList) {
            list.add(BookmarkNovelDto.builder()
                    .id(bookmarkNovelEntity.getId())
                    .novelId(bookmarkNovelEntity.getTarget().getId())
                    .novelName(bookmarkNovelEntity.getTarget().getNovelName())
                    .novelImageUrl(bookmarkNovelEntity.getTarget().getNovelImageUrl())
                    .build());
        }
        return list;
    }
}
