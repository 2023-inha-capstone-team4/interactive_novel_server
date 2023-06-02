package com.capstone.interactive_novel.novel.domain;

import com.capstone.interactive_novel.common.type.PublisherType;
import com.capstone.interactive_novel.novel.type.NovelStatus;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NovelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ReaderEntity reader;

    @ManyToOne
    private PublisherEntity publisher;

    @Column(unique = true, columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String novelName;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String authorName;

    private Long authorId;

    private Long totalScore;
    private Long reviewerAmount;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String novelIntroduce;

    private String novelImageUrl;

    @Enumerated(EnumType.STRING)
    private NovelStatus novelStatus;

    @Enumerated(EnumType.STRING)
    private PublisherType publisherType;

    public static NovelEntity createNovelByReader(ReaderEntity reader, String novelName, String authorName, Long authorId, String novelIntroduce, String imageUrl) {
        return NovelEntity.builder()
                .reader(reader)
                .novelName(novelName)
                .authorName(authorName)
                .authorId(authorId)
                .novelIntroduce(novelIntroduce)
                .novelImageUrl(imageUrl)
                .novelStatus(NovelStatus.FREE)
                .publisherType(PublisherType.READER)
                .totalScore(0L)
                .build();
    }

    public static NovelEntity createNovelByPublisher(PublisherEntity publisher, String novelName, String authorName, Long authorId, String novelIntroduce, String imageUrl, NovelStatus novelStatus) {
        return NovelEntity.builder()
                .publisher(publisher)
                .novelName(novelName)
                .authorName(authorName)
                .authorId(authorId)
                .novelIntroduce(novelIntroduce)
                .novelImageUrl(imageUrl)
                .novelStatus(novelStatus)
                .publisherType(PublisherType.PUBLISHER)
                .totalScore(0L)
                .build();
    }
}
