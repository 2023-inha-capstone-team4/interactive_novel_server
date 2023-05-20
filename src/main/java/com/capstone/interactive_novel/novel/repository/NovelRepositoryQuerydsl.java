package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelStatus;
import com.capstone.interactive_novel.novel.dto.NovelDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.capstone.interactive_novel.novel.domain.QNovelEntity.novelEntity;

@RequiredArgsConstructor
@Repository
public class NovelRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    public List<NovelDto> viewListOfNewNovel() {
        return jpaQueryFactory.select(Projections.constructor(NovelDto.class,
                        novelEntity.id,
                        novelEntity.novelName,
                        novelEntity.authorName,
                        novelEntity.authorId,
                        novelEntity.novelIntroduce,
                        novelEntity.publisherType,
                        novelEntity.novelImageUrl,
                        novelEntity.totalScore))
                .from(novelEntity)
                .where(novelEntity.novelStatus.ne(NovelStatus.DEACTIVATED))
                .limit(10)
                .orderBy(novelEntity.id.desc())
                .fetch();
    }

    public List<NovelDto> viewListOfPopularNovel(long startIdx, long endIdx) {
        return jpaQueryFactory.select(Projections.constructor(NovelDto.class,
                        novelEntity.id,
                        novelEntity.novelName,
                        novelEntity.authorName,
                        novelEntity.authorId,
                        novelEntity.novelIntroduce,
                        novelEntity.publisherType,
                        novelEntity.novelImageUrl,
                        novelEntity.totalScore))
                .from(novelEntity)
                .where(novelEntity.novelStatus.ne(NovelStatus.DEACTIVATED))
                .orderBy(novelEntity.totalScore.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }
}
