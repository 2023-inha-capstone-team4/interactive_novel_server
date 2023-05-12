package com.capstone.interactive_novel.bookmark.controller;

import com.capstone.interactive_novel.bookmark.dto.BookmarkNovelDto;
import com.capstone.interactive_novel.bookmark.dto.BookmarkPublisherDto;
import com.capstone.interactive_novel.bookmark.dto.BookmarkReaderDto;
import com.capstone.interactive_novel.bookmark.service.BookmarkService;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/novel/{novelId}/set")
    public ResponseEntity<Long> bookmarkNovel(@AuthenticationPrincipal ReaderEntity reader,
                                              @PathVariable Long novelId) {
        bookmarkService.bookmarkNovel(reader, novelId);
        return ResponseEntity.created(URI.create("/bookmark/novel/" + novelId)).build();
    }

    @GetMapping("/novel/{novelId}")
    public ResponseEntity<Boolean> isBookmarkedNovel(@AuthenticationPrincipal ReaderEntity reader,
                                                     @PathVariable Long novelId) {
        return ResponseEntity.ok(bookmarkService.isBookmarkedNovel(reader, novelId));
    }

    @GetMapping("/novel")
    public ResponseEntity<List<BookmarkNovelDto>> viewAllBookmarkedNovelList(@AuthenticationPrincipal ReaderEntity reader) {
        return ResponseEntity.ok(bookmarkService.viewAllBookmarkedNovelList(reader));
    }

    @PostMapping("/reader/{readerId}/set")
    public ResponseEntity<Long> bookmarkReader(@AuthenticationPrincipal ReaderEntity reader,
                                               @PathVariable Long readerId) {
        bookmarkService.bookmarkReader(reader, readerId);
        return ResponseEntity.created(URI.create("/bookmark/reader/" + readerId)).build();
    }

    @GetMapping("/reader/{readerId}")
    public ResponseEntity<Boolean> isBookmarkedReader(@AuthenticationPrincipal ReaderEntity reader,
                                                      @PathVariable Long readerId) {
        return ResponseEntity.ok(bookmarkService.isBookmarkedReader(reader, readerId));
    }

    @GetMapping("/reader")
    public ResponseEntity<List<BookmarkReaderDto>> viewAllBookmarkedReaderList(@AuthenticationPrincipal ReaderEntity reader) {
        return ResponseEntity.ok(bookmarkService.viewAllBookmarkedReaderList(reader));
    }

    @PostMapping("/publisher/{publisherId}/set")
    public ResponseEntity<Long> bookmarkPublisher(@AuthenticationPrincipal ReaderEntity reader,
                                                  @PathVariable Long publisherId) {
        bookmarkService.bookmarkPublisher(reader, publisherId);
        return ResponseEntity.created(URI.create("/bookmark/publisher/" + publisherId)).build();
    }

    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<Boolean> isBookmarkedPublisher(@AuthenticationPrincipal ReaderEntity reader,
                                                         @PathVariable Long publisherId) {
        return ResponseEntity.ok(bookmarkService.isBookmarkedPublisher(reader, publisherId));
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<BookmarkPublisherDto>> viewAllBookmarkedPublisherList(@AuthenticationPrincipal ReaderEntity reader) {
        return ResponseEntity.ok(bookmarkService.viewAllBookmarkedPublisherList(reader));
    }

}
