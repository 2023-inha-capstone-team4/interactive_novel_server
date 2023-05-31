package com.capstone.interactive_novel.fcm.repository;

import com.capstone.interactive_novel.fcm.domain.FcmTokenEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, String> {
    Optional<FcmTokenEntity> findByReader(ReaderEntity reader);
}
