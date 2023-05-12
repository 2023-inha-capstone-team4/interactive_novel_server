package com.capstone.interactive_novel.publisher.repository;

import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, String> {
    Optional<PublisherEntity> findById(long id);
    Optional<PublisherEntity> findByEmail(String email);

}
