package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.type.NovelCategoryType;
import com.capstone.interactive_novel.novel.type.NovelStatus;
import com.capstone.interactive_novel.novel.dto.NovelDto;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.capstone.interactive_novel.novel.domain.QNovelEntity.novelEntity;

@RequiredArgsConstructor
@Repository
public class NovelRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    public List<NovelDto> viewListOfNewNovel() {
        return entityListToDtoList(jpaQueryFactory
                .selectFrom(novelEntity)
                .where(novelEntity.novelStatus.ne(NovelStatus.DEACTIVATED))
                .limit(10)
                .orderBy(novelEntity.id.desc())
                .fetch());
    }

    public List<NovelDto> viewListOfPopularNovel(long startIdx, long endIdx) {
        return entityListToDtoList(jpaQueryFactory
                .selectFrom(novelEntity)
                .where(novelEntity.novelStatus.ne(NovelStatus.DEACTIVATED))
                .orderBy(novelEntity.totalScore.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch());
    }

    public List<NovelDto> viewListOfNovelAboutReader(ReaderEntity reader, long startIdx, long endIdx) {
        return entityListToDtoList(jpaQueryFactory
                .selectFrom(novelEntity)
                .where(novelEntity.novelStatus.ne(NovelStatus.DEACTIVATED).and(novelEntity.reader.eq(reader)))
                .orderBy(novelEntity.totalScore.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch());
    }

    public List<NovelDto> viewListOfNovelAboutPublisher(PublisherEntity publisher, long startIdx, long endIdx) {
        return entityListToDtoList(jpaQueryFactory
                .selectFrom(novelEntity)
                .where(novelEntity.novelStatus.ne(NovelStatus.DEACTIVATED).and(novelEntity.publisher.eq(publisher)))
                .orderBy(novelEntity.totalScore.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch());
    }

    public List<NovelDto> viewListOfKeywordSearchNovel(String keyword, long startIdx, long endIdx) {
        return entityListToDtoList(jpaQueryFactory
                .selectFrom(novelEntity)
                .where(novelEntity.novelStatus.ne(NovelStatus.DEACTIVATED).and(novelEntity.novelName.like("%" + keyword + "%")))
                .orderBy(novelEntity.totalScore.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch());
    }

    public List<NovelDto> viewListOfCategorizedNovel(NovelCategoryType category, long startIdx, long endIdx) {
        return entityListToDtoList(jpaQueryFactory
                .selectFrom(novelEntity)
                .where(novelEntity.novelStatus.ne(NovelStatus.DEACTIVATED).and(novelEntity.novelCategoryTypeList.contains(category)))
                .orderBy(novelEntity.totalScore.desc())
                .offset(startIdx)
                .limit(endIdx - startIdx + 1)
                .fetch());
    }

    private List<NovelDto> entityListToDtoList(List<NovelEntity> novelEntityList) {
        List<NovelDto> novelDtoList = new ArrayList<>();
        for(NovelEntity novel : novelEntityList) {
            novelDtoList.add(NovelDto.entityToDto(novel));
        }
        return novelDtoList;
    }
}
