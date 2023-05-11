package com.capstone.interactive_novel.bookmark.dto;

import com.capstone.interactive_novel.bookmark.domain.BookmarkReaderEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BookmarkReaderDto {
    private long id;
    private long targetId;
    private String targetName;
    private String targetImageUrl;

    public static List<BookmarkReaderDto> createBookmarkReaderList(List<BookmarkReaderEntity> bookmarkReaderList) {
        List<BookmarkReaderDto> list = new ArrayList<>();
        for (BookmarkReaderEntity bookmarkReaderEntity : bookmarkReaderList) {
            list.add(BookmarkReaderDto.builder()
                    .id(bookmarkReaderEntity.getId())
                    .targetId(bookmarkReaderEntity.getTarget().getId())
                    .targetName(bookmarkReaderEntity.getTarget().getUsername())
                    .targetImageUrl(bookmarkReaderEntity.getTarget().getProfileImgUrl())
                    .build());
        }
        return list;
    }
}
