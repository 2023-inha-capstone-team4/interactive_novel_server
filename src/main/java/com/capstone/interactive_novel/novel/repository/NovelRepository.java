package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelRepository extends JpaRepository<NovelEntity, String> {
    Optional<NovelEntity> findByNovelName(String novelName);
    Optional<NovelEntity> findById(Long id);
}
