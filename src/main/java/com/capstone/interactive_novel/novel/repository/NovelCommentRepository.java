package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelCommentRepository extends JpaRepository<NovelCommentEntity, String> {
    Optional<NovelCommentEntity> findById(long id);
}
