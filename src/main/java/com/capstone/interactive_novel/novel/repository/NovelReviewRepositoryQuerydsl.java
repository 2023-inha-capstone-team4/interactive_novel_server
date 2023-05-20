package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.domain.NovelReviewStatus;
import com.capstone.interactive_novel.novel.dto.NovelReviewDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.capstone.interactive_novel.novel.domain.QNovelReviewEntity.novelReviewEntity;

@Slf4j
@RequiredArgsConstructor
@Repository
public class NovelReviewRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;
    public List<NovelReviewDto> viewListOfNewNovelReviewByDesc(long startIdx, long endIdx, NovelEntity novel) {
        return jpaQueryFactory.select(Projections.constructor(NovelReviewDto.class,
                        novelReviewEntity.id,
                        novelReviewEntity.reviewerName,
                        novelReviewEntity.reviewerId,
                        novelReviewEntity.review,
                        novelReviewEntity.novel.id,
                        novelReviewEntity.novelScore,
                        novelReviewEntity.publisherType))
                .from(novelReviewEntity)
                .where(novelReviewEntity.novelReviewStatus.ne(NovelReviewStatus.DEACTIVATED).and(novelReviewEntity.novel.eq(novel)))
                .orderBy(novelReviewEntity.id.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }

    public List<NovelReviewDto> viewListOfNewNovelReviewByAsc(long startIdx, long endIdx, NovelEntity novel) {
        return jpaQueryFactory.select(Projections.constructor(NovelReviewDto.class,
                        novelReviewEntity.id,
                        novelReviewEntity.reviewerName,
                        novelReviewEntity.reviewerId,
                        novelReviewEntity.review,
                        novelReviewEntity.novel.id,
                        novelReviewEntity.novelScore,
                        novelReviewEntity.publisherType))
                .from(novelReviewEntity)
                .where(novelReviewEntity.novelReviewStatus.ne(NovelReviewStatus.DEACTIVATED).and(novelReviewEntity.novel.eq(novel)))
                .orderBy(novelReviewEntity.id.asc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }

    public List<NovelReviewDto> viewListOfPopularNovelReviewByDesc(long startIdx, long endIdx, NovelEntity novel) {
        return jpaQueryFactory.select(Projections.constructor(NovelReviewDto.class,
                        novelReviewEntity.id,
                        novelReviewEntity.reviewerName,
                        novelReviewEntity.reviewerId,
                        novelReviewEntity.review,
                        novelReviewEntity.novel.id,
                        novelReviewEntity.novelScore,
                        novelReviewEntity.publisherType))
                .from(novelReviewEntity)
                .where(novelReviewEntity.novelReviewStatus.ne(NovelReviewStatus.DEACTIVATED).and(novelReviewEntity.novel.eq(novel)))
                .orderBy(novelReviewEntity.novelScore.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }

    public List<NovelReviewDto> viewListOfPopularNovelReviewByAsc(long startIdx, long endIdx, NovelEntity novel) {
        return jpaQueryFactory.select(Projections.constructor(NovelReviewDto.class,
                        novelReviewEntity.id,
                        novelReviewEntity.reviewerName,
                        novelReviewEntity.reviewerId,
                        novelReviewEntity.review,
                        novelReviewEntity.novel.id,
                        novelReviewEntity.novelScore,
                        novelReviewEntity.publisherType))
                .from(novelReviewEntity)
                .where(novelReviewEntity.novelReviewStatus.ne(NovelReviewStatus.DEACTIVATED).and(novelReviewEntity.novel.eq(novel)))
                .orderBy(novelReviewEntity.novelScore.asc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }
}
