package com.capstone.interactive_novel.bookmark.repository;

import com.capstone.interactive_novel.bookmark.domain.BookmarkNovelEntity;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkNovelRepository extends JpaRepository<BookmarkNovelEntity, String> {
    Optional<List<BookmarkNovelEntity>> findAllByReader(ReaderEntity reader);
    Optional<BookmarkNovelEntity> findByReaderAndTarget(ReaderEntity reader, NovelEntity target);
}
