package com.capstone.interactive_novel.novel.controller;

import com.capstone.interactive_novel.novel.dto.*;
import com.capstone.interactive_novel.novel.service.NovelDetailService;
import com.capstone.interactive_novel.novel.service.NovelService;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelController {
    private final NovelService novelService;
    private final NovelDetailService novelDetailService;
    // Reader 관련

    @PostMapping("/reader")
    public ResponseEntity<NovelDto> createNovelByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                        @RequestPart MultipartFile file,
                                                        @RequestPart String novelName,
                                                        @RequestPart String novelIntroduce) {
        return ResponseEntity.ok(novelService.createNovelByReader(reader, file, novelName, novelIntroduce));
    }

    @PatchMapping("/reader/{novelId}/modify")
    ResponseEntity<NovelDto> modifyNovelByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                 @PathVariable Long novelId,
                                                 @RequestPart(required = false) MultipartFile file,
                                                 @RequestPart(required = false) String novelIntroduce) {
        return ResponseEntity.ok(novelService.modifyNovelByReader(reader, novelId, file, novelIntroduce));
    }

    @PatchMapping("/reader/{novelId}/deactivate")
    ResponseEntity<String> deactivateNovelByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                   @PathVariable Long novelId) {
        novelService.deactivateNovelByReader(reader, novelId);
        return ResponseEntity.ok("Deactivated");
    }

    @PostMapping("/reader/{novelId}")
    public ResponseEntity<NovelDetailDto> createNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                                    @PathVariable Long novelId,
                                                                    @RequestPart MultipartFile file,
                                                                    @RequestPart String novelDetailName,
                                                                    @RequestPart String novelDetailIntroduce,
                                                                    @RequestPart MultipartFile novelDataFile,
                                                                    @RequestPart NovelDetailMediaDto mediaDto) {
        return ResponseEntity.ok(novelDetailService.createNovelDetailByReader(reader, novelId, file, novelDetailName, novelDetailIntroduce, novelDataFile, mediaDto));
    }

    @PostMapping("/reader/{novelId}/{novelDetailId}/modify")
    public ResponseEntity<NovelDetailDto> modifyNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                       @PathVariable Long novelId,
                                                       @PathVariable Long novelDetailId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String novelDetailName,
                                                       @RequestPart String novelDetailIntroduce,
                                                       @RequestPart MultipartFile novelDataFile,
                                                       @RequestPart NovelDetailMediaDto mediaDto) {
        return ResponseEntity.ok(novelDetailService.modifyNovelDetailByReader(reader, novelId, novelDetailId,file, novelDetailName, novelDetailIntroduce, novelDataFile, mediaDto));
    }

    @PatchMapping("/reader/{novelId}/{novelDetailId}/deactivate")
    public ResponseEntity<String> deactivateNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                                @PathVariable Long novelId,
                                                                @PathVariable Long novelDetailId) {
        novelDetailService.deactivateNovelDetailByReader(reader, novelId, novelDetailId);
        return ResponseEntity.ok("Deactivated");
    }

    @PostMapping("/reader/{novelId}/{novelDetailId}/uploadFile")
    public ResponseEntity<List<String>> uploadFileOnNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                                        @PathVariable Long novelId,
                                                                        @PathVariable Long novelDetailId,
                                                                        @RequestPart MultipartFile[] files,
                                                                        @RequestPart String fileType) {
        return ResponseEntity.ok(novelDetailService.uploadFilesByReader(reader, novelId, novelDetailId, files, fileType));
    }

    // Publisher 관련

    @PostMapping("/publisher")
    public ResponseEntity<NovelDto> createNovelByPublisher(@AuthenticationPrincipal PublisherEntity publisher,
                                                           @RequestPart MultipartFile file,
                                                           @RequestPart String novelName,
                                                           @RequestPart String novelIntroduce,
                                                           @RequestPart String payType) {
        return ResponseEntity.ok(novelService.createNovelByPublisher(publisher, file, novelName, novelIntroduce, payType));
    }

    // 전체 관련
    @GetMapping("/list/new")
    public ResponseEntity<List<NovelDto>> viewListOfNewNovel() {
        return ResponseEntity.ok(novelService.viewListOfNewNovel());
    }

    @GetMapping("/list/popular")
    public ResponseEntity<List<NovelDto>> viewListOfPopularNovel(@RequestParam("startIdx") long startIdx,
                                                                 @RequestParam("endIdx") long endIdx) {
        return ResponseEntity.ok(novelService.viewListOfPopularNovel(startIdx, endIdx));
    }

    @GetMapping("/score")
    public ResponseEntity<Double> viewNovelAverageScore(@RequestParam("novelId") Long novelId) {
        return ResponseEntity.ok(novelService.viewNovelAverageScore(novelId));
    }
}
