package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelDetailStatus;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.dto.NovelDetailListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.capstone.interactive_novel.novel.domain.QNovelDetailEntity.novelDetailEntity;

@RequiredArgsConstructor
@Repository
public class NovelDetailRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    public void updateAllNovelDetailStatus(Long novelId, NovelDetailStatus status) {
        jpaQueryFactory.update(novelDetailEntity)
                .set(novelDetailEntity.novelDetailStatus, status)
                .where(novelDetailEntity.novel.id.eq(novelId))
                .execute();
    }

    public List<NovelDetailListDto> viewListOfNovelDetailByDesc(NovelEntity novel, long startIdx, long endIdx) {
        return jpaQueryFactory.select(Projections.constructor(NovelDetailListDto.class,
                        novelDetailEntity.id,
                        novelDetailEntity.novelDetailName,
                        novelDetailEntity.novel.id,
                        novelDetailEntity.novel.novelName,
                        novelDetailEntity.novel.authorId,
                        novelDetailEntity.novel.authorName,
                        novelDetailEntity.publisherType,
                        novelDetailEntity.novelDetailImageUrl))
                .from(novelDetailEntity)
                .where(novelDetailEntity.novelDetailStatus.ne(NovelDetailStatus.DEACTIVATED).and(novelDetailEntity.novel.eq(novel)))
                .orderBy(novelDetailEntity.id.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }

    public List<NovelDetailListDto> viewListOfNovelDetailByAsc(NovelEntity novel, long startIdx, long endIdx) {
        return jpaQueryFactory.select(Projections.constructor(NovelDetailListDto.class,
                        novelDetailEntity.id,
                        novelDetailEntity.novelDetailName,
                        novelDetailEntity.novel.id,
                        novelDetailEntity.novel.novelName,
                        novelDetailEntity.novel.authorId,
                        novelDetailEntity.novel.authorName,
                        novelDetailEntity.publisherType,
                        novelDetailEntity.novelDetailImageUrl))
                .from(novelDetailEntity)
                .where(novelDetailEntity.novelDetailStatus.ne(NovelDetailStatus.DEACTIVATED).and(novelDetailEntity.novel.eq(novel)))
                .orderBy(novelDetailEntity.id.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch();
    }
}
