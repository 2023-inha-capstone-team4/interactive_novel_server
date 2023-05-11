package com.capstone.interactive_novel.bookmark.service;

import com.capstone.interactive_novel.bookmark.domain.BookmarkNovelEntity;
import com.capstone.interactive_novel.bookmark.domain.BookmarkReaderEntity;
import com.capstone.interactive_novel.bookmark.dto.BookmarkNovelDto;
import com.capstone.interactive_novel.bookmark.dto.BookmarkReaderDto;
import com.capstone.interactive_novel.bookmark.repository.BookmarkNovelRepository;
import com.capstone.interactive_novel.bookmark.repository.BookmarkReaderRepository;
import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.repository.NovelRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final NovelRepository novelRepository;
    private final ReaderRepository readerRepository;
    private final BookmarkNovelRepository bookmarkNovelRepository;
    private final BookmarkReaderRepository bookmarkReaderRepository;

    public void bookmarkNovel(ReaderEntity reader, Long novelId) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));

        if(novel.getReader().getId() == reader.getId()) {
            throw new INovelException(CANNOT_BOOKMARK_OWN_NOVEL);
        }

        bookmarkNovelRepository.findByReaderAndTarget(reader, novel)
                .ifPresentOrElse(bookmarkNovelRepository::delete,
                        () -> bookmarkNovelRepository.save(BookmarkNovelEntity.builder()
                                .reader(reader)
                                .target(novel)
                                .build()));
    }

    public boolean isBookmarkedNovel(ReaderEntity reader, Long novelId) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));

        return bookmarkNovelRepository.findByReaderAndTarget(reader, novel).isPresent();
    }

    public List<BookmarkNovelDto> viewAllBookmarkedNovelList(ReaderEntity reader) {
        List<BookmarkNovelEntity> bookmarkedNovelList = bookmarkNovelRepository.findAllByReader(reader)
                .orElseThrow(() -> new INovelException(NOVEL_BOOKMARK_LIST_NOT_FOUND));

        return BookmarkNovelDto.createBookmarkNovelList(bookmarkedNovelList);
    }

    public void bookmarkReader(ReaderEntity reader, Long readerId) {
        ReaderEntity target = readerRepository.findById(readerId)
                .orElseThrow(() -> new INovelException(USER_NOT_FOUND));

        if(target.getId() == reader.getId()) {
            throw new INovelException(CANNOT_BOOKMARK_YOURSELF);
        }

        bookmarkReaderRepository.findByReaderAndTarget(reader, target)
                .ifPresentOrElse(bookmarkReaderRepository::delete,
                        () -> bookmarkReaderRepository.save(BookmarkReaderEntity.builder()
                                .reader(reader)
                                .target(target)
                                .build()));
    }

    public boolean isBookmarkedReader(ReaderEntity reader, Long readerId) {
        ReaderEntity target = readerRepository.findById(readerId)
                .orElseThrow(() -> new INovelException(USER_NOT_FOUND));

        return bookmarkReaderRepository.findByReaderAndTarget(reader, target).isPresent();
    }

    public List<BookmarkReaderDto> viewAllBookmarkedReaderList(ReaderEntity target) {
        List<BookmarkReaderEntity> bookmarkedReaderList = bookmarkReaderRepository.findAllByReader(target)
                .orElseThrow(() -> new INovelException(READER_BOOKMARK_LIST_NOT_FOUND));

        return BookmarkReaderDto.createBookmarkReaderList(bookmarkedReaderList);
    }
}
