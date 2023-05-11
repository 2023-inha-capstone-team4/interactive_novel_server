package com.capstone.interactive_novel.novel.repository;

import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelDetailRepository extends JpaRepository<NovelDetailEntity, String> {
    Optional<NovelDetailEntity> findById(long id);
}
