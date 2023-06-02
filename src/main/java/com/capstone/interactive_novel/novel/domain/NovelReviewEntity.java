package com.capstone.interactive_novel.novel.domain;

import com.capstone.interactive_novel.common.type.PublisherType;
import com.capstone.interactive_novel.novel.type.NovelReviewStatus;
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
public class NovelReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ReaderEntity reader;

    @ManyToOne
    private PublisherEntity publisher;

    @ManyToOne
    private NovelEntity novel;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String reviewerName;
    private Long reviewerId;

    @Enumerated(EnumType.STRING)
    private PublisherType publisherType;

    @Enumerated(EnumType.STRING)
    private NovelReviewStatus novelReviewStatus;

    private int novelScore;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String review;
}
