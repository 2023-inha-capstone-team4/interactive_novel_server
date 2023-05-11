package com.capstone.interactive_novel.novel.domain;

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

    @Column(unique = true)
    private String novelName;

    private Long totalScore;

    private String novelIntroduce;

    private String NovelImageUrl;

    @Enumerated(EnumType.STRING)
    private NovelStatus novelStatus;

    @Enumerated(EnumType.STRING)
    private NovelPublisherType novelPublisherType;

    public static NovelEntity createNovelByReader(ReaderEntity reader, String novelName, String novelIntroduce, String imageUrl) {
        return NovelEntity.builder()
                .reader(reader)
                .novelName(novelName)
                .novelIntroduce(novelIntroduce)
                .NovelImageUrl(imageUrl)
                .novelStatus(NovelStatus.FREE)
                .novelPublisherType(NovelPublisherType.READER)
                .totalScore(0L)
                .build();
    }
}
