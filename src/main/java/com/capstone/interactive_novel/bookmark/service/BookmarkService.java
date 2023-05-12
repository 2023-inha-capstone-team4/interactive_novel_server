package com.capstone.interactive_novel.bookmark.service;

import com.capstone.interactive_novel.bookmark.domain.BookmarkNovelEntity;
import com.capstone.interactive_novel.bookmark.domain.BookmarkPublisherEntity;
import com.capstone.interactive_novel.bookmark.domain.BookmarkReaderEntity;
import com.capstone.interactive_novel.bookmark.dto.BookmarkNovelDto;
import com.capstone.interactive_novel.bookmark.dto.BookmarkPublisherDto;
import com.capstone.interactive_novel.bookmark.dto.BookmarkReaderDto;
import com.capstone.interactive_novel.bookmark.repository.BookmarkNovelRepository;
import com.capstone.interactive_novel.bookmark.repository.BookmarkPublisherRepository;
import com.capstone.interactive_novel.bookmark.repository.BookmarkReaderRepository;
import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.repository.NovelRepository;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.publisher.repository.PublisherRepository;
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
    private final PublisherRepository publisherRepository;
    private final BookmarkNovelRepository bookmarkNovelRepository;
    private final BookmarkReaderRepository bookmarkReaderRepository;
    private final BookmarkPublisherRepository bookmarkPublisherRepository;

    public void bookmarkNovel(ReaderEntity reader, Long novelId) {
        NovelEntity target = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));

        if(target.getReader().getId() == reader.getId()) {
            throw new INovelException(CANNOT_BOOKMARK_OWN_NOVEL);
        }

        bookmarkNovelRepository.findByReaderAndTarget(reader, target)
                .ifPresentOrElse(bookmarkNovelRepository::delete,
                        () -> bookmarkNovelRepository.save(BookmarkNovelEntity.builder()
                                .reader(reader)
                                .target(target)
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

    public List<BookmarkReaderDto> viewAllBookmarkedReaderList(ReaderEntity reader) {
        List<BookmarkReaderEntity> bookmarkedReaderList = bookmarkReaderRepository.findAllByReader(reader)
                .orElseThrow(() -> new INovelException(READER_BOOKMARK_LIST_NOT_FOUND));

        return BookmarkReaderDto.createBookmarkReaderList(bookmarkedReaderList);
    }

    public void bookmarkPublisher(ReaderEntity reader, Long publisherId) {
        PublisherEntity target = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new INovelException(USER_NOT_FOUND));

        bookmarkPublisherRepository.findByReaderAndTarget(reader, target)
                .ifPresentOrElse(bookmarkPublisherRepository::delete,
                        () -> bookmarkPublisherRepository.save(BookmarkPublisherEntity.builder()
                                .reader(reader)
                                .target(target)
                                .build()));
    }

    public boolean isBookmarkedPublisher(ReaderEntity reader, Long publisherId) {
        PublisherEntity target = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new INovelException(USER_NOT_FOUND));

        return bookmarkPublisherRepository.findByReaderAndTarget(reader, target).isPresent();
    }

    public List<BookmarkPublisherDto> viewAllBookmarkedPublisherList(ReaderEntity reader) {
        List<BookmarkPublisherEntity> bookmarkedPublisherList = bookmarkPublisherRepository.findAllByReader(reader)
                .orElseThrow(() -> new INovelException(PUBLISHER_BOOKMARK_LIST_NOT_FOUND));

        return BookmarkPublisherDto.createBookmarkPublisherList(bookmarkedPublisherList);
    }
}
