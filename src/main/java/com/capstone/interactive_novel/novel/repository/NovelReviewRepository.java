package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.domain.NovelReviewEntity;
import com.capstone.interactive_novel.novel.type.NovelReviewStatus;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelReviewRepository extends JpaRepository<NovelReviewEntity, String> {
    Optional<NovelReviewEntity> findById(long id);
    Optional<NovelReviewEntity> findByReaderAndNovelAndNovelReviewStatus(ReaderEntity reader, NovelEntity novel, NovelReviewStatus status);

}
