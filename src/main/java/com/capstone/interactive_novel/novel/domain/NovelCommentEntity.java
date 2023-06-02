package com.capstone.interactive_novel.novel.domain;

import com.capstone.interactive_novel.novel.type.NovelCommentStatus;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.*;

import javax.persistence.*;

import static com.capstone.interactive_novel.novel.type.NovelCommentStatus.AVAILABLE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NovelCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ReaderEntity reader;

    @ManyToOne
    private NovelDetailEntity novelDetail;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String comment;

    private Long recommendAmount;

    @Enumerated(EnumType.STRING)
    private NovelCommentStatus novelCommentStatus;

    public static NovelCommentEntity setComment(ReaderEntity reader, NovelDetailEntity novelDetail, Long recommendAmount, String comment) {
        return NovelCommentEntity.builder()
                .reader(reader)
                .novelDetail(novelDetail)
                .comment(comment)
                .recommendAmount(recommendAmount)
                .novelCommentStatus(AVAILABLE)
                .build();
    }
}
