package com.capstone.interactive_novel.bookmark.repository;

import com.capstone.interactive_novel.bookmark.domain.BookmarkReaderEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkReaderRepository extends JpaRepository<BookmarkReaderEntity, String> {
    Optional<BookmarkReaderEntity> findByReaderAndTarget(ReaderEntity reader, ReaderEntity target);
    Optional<List<BookmarkReaderEntity>> findAllByReader(ReaderEntity reader);

}
