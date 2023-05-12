package com.capstone.interactive_novel.bookmark.repository;

import com.capstone.interactive_novel.bookmark.domain.BookmarkPublisherEntity;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkPublisherRepository extends JpaRepository<BookmarkPublisherEntity, String> {
    Optional<BookmarkPublisherEntity> findByReaderAndTarget(ReaderEntity reader, PublisherEntity target);
    Optional<List<BookmarkPublisherEntity>> findAllByReader(ReaderEntity reader);
}
