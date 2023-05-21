package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelCommentEntity;
import com.capstone.interactive_novel.novel.domain.NovelCommentRecommendEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelCommentRecommendRepository extends JpaRepository<NovelCommentRecommendEntity, String> {
    Optional<NovelCommentRecommendEntity> findByReaderAndNovelComment(ReaderEntity reader, NovelCommentEntity novelComment);
}
