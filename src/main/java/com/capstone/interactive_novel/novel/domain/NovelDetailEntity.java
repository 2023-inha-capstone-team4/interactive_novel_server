package com.capstone.interactive_novel.novel.domain;

import com.capstone.interactive_novel.common.utils.FileConvertUtils;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NovelDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String novelDetailName;

    private String novelDetailIntroduce;

    @ManyToOne
    private NovelEntity novel;

    private String novelDetailImageUrl;

    private Long detailScore;

    @Enumerated(EnumType.STRING)
    private NovelPublisherType novelPublisherType;

    @Lob
    private String novelData;

    @OneToMany
    private List<NovelDetailImageEntity> imageList;

    @OneToMany
    private List<NovelDetailSoundEntity> soundList;

    public static NovelDetailEntity createNovelDetail(String novelDetailName, String novelDetailIntroduce, String imageUrl, NovelEntity novel, MultipartFile novelScriptFile) {
        return NovelDetailEntity.builder()
                .novelDetailName(novelDetailName)
                .novelDetailIntroduce(novelDetailIntroduce)
                .novelDetailImageUrl(imageUrl)
                .novelPublisherType(NovelPublisherType.READER)
                .novel(novel)
                .detailScore(0L)
                .novelData(FileConvertUtils.fileToLobConverter(novelScriptFile))
                .build();
    }
}
