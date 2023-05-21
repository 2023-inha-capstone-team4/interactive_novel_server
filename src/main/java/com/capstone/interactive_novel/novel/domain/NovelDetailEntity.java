package com.capstone.interactive_novel.novel.domain;

import com.capstone.interactive_novel.common.type.PublisherType;
import com.capstone.interactive_novel.common.utils.FileUtils;
import com.capstone.interactive_novel.novel.dto.NovelDetailMediaDto;
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
    private long id;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String novelDetailName;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String novelDetailIntroduce;

    @ManyToOne
    private NovelEntity novel;

    private String novelDetailImageUrl;

    private Long detailScore;

    @Enumerated(EnumType.STRING)
    private NovelDetailStatus novelDetailStatus;

    @Enumerated(EnumType.STRING)
    private PublisherType publisherType;

    @ElementCollection(fetch = FetchType.LAZY)
    List<String> imageList;

    @ElementCollection(fetch = FetchType.LAZY)
    List<String> soundList;

    @Lob
    private String novelData;

    public static NovelDetailEntity setNovelDetail(String novelDetailName, String novelDetailIntroduce, String imageUrl, NovelEntity novel, MultipartFile novelScriptFile, NovelDetailMediaDto mediaDto) {
        return NovelDetailEntity.builder()
                .novelDetailName(novelDetailName)
                .novelDetailIntroduce(novelDetailIntroduce)
                .novelDetailImageUrl(imageUrl)
                .publisherType(PublisherType.READER)
                .novel(novel)
                .detailScore(0L)
                .novelData(FileUtils.fileToLobConverter(novelScriptFile))
                .imageList(mediaDto.getImagelist())
                .soundList(mediaDto.getSoundList())
                .novelDetailStatus(NovelDetailStatus.AVAILABLE)
                .build();
    }
}
