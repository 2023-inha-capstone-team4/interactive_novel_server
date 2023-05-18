package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelDetailStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
