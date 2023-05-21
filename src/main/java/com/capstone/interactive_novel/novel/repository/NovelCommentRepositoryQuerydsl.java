package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelCommentStatus;
import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import com.capstone.interactive_novel.novel.dto.NovelCommentDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.capstone.interactive_novel.novel.domain.QNovelCommentEntity.novelCommentEntity;

@Repository
@RequiredArgsConstructor
public class NovelCommentRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    public List<NovelCommentDto> viewListOfNewCommentByDesc(long startIdx, long endIdx, NovelDetailEntity novelDetail) {
        return jpaQueryFactory.select(Projections.constructor(NovelCommentDto.class,
                                novelCommentEntity.id,
                                novelCommentEntity.reader.id,
                                novelCommentEntity.reader.userName,
                                novelCommentEntity.recommendAmount,
                                novelCommentEntity.comment))
                .from(novelCommentEntity)
                .where(novelCommentEntity.novelCommentStatus.ne(NovelCommentStatus.DEACTIVATED).and(novelCommentEntity.novelDetail.eq(novelDetail)))
                .orderBy(novelCommentEntity.id.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }

    public List<NovelCommentDto> viewListOfPopularCommentByDesc(long startIdx, long endIdx, NovelDetailEntity novelDetail) {
        return jpaQueryFactory.select(Projections.constructor(NovelCommentDto.class,
                                novelCommentEntity.id,
                                novelCommentEntity.reader.id,
                                novelCommentEntity.reader.userName,
                                novelCommentEntity.recommendAmount,
                                novelCommentEntity.comment))
                .from(novelCommentEntity)
                .where(novelCommentEntity.novelCommentStatus.ne(NovelCommentStatus.DEACTIVATED).and(novelCommentEntity.novelDetail.eq(novelDetail)))
                .orderBy(novelCommentEntity.recommendAmount.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }

}
