package com.capstone.interactive_novel.novel.dto;

import com.capstone.interactive_novel.common.type.PublisherType;
import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NovelDetailDto {
    private long id;
    private long novelId;
    private String novelDetailName;
    private String novelDetailIntroduce;
    private Long authorId;
    private String authorName;
    @Enumerated(EnumType.STRING)
    private PublisherType publisherType;
    private String novelDetailImageUrl;
    private List<String> imageList;
    private List<String> soundList;
    private String novelData;

    public static NovelDetailDto entityToDto(NovelDetailEntity novelDetail) {
            return NovelDetailDto.builder()
                    .id(novelDetail.getId())
                    .novelId(novelDetail.getNovel().getId())
                    .novelDetailName(novelDetail.getNovelDetailName())
                    .novelDetailIntroduce(novelDetail.getNovelDetailIntroduce())
                    .publisherType(novelDetail.getPublisherType())
                    .authorId(novelDetail.getNovel().getAuthorId())
                    .authorName(novelDetail.getNovel().getAuthorName())
                    .novelDetailImageUrl(novelDetail.getNovelDetailImageUrl())
                    .imageList(novelDetail.getImageList())
                    .soundList(novelDetail.getSoundList())
                    .novelData(novelDetail.getNovelData())
                    .build();
    }
}
