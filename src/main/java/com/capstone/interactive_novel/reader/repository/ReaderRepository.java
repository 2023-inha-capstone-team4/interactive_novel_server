package com.capstone.interactive_novel.reader.repository;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<ReaderEntity, String> {
    Optional<ReaderEntity> findById(long id);
    Optional<ReaderEntity> findByEmail(String email);
    Optional<ReaderEntity> findByEmailAuthKey(String emailAuthKey);
}
