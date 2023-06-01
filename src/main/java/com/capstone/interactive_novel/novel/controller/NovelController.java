package com.capstone.interactive_novel.novel.controller;

import com.capstone.interactive_novel.novel.dto.*;
import com.capstone.interactive_novel.novel.service.NovelDetailService;
import com.capstone.interactive_novel.novel.service.NovelService;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.capstone.interactive_novel.common.document.SwaggerDocument.documentAboutNovelController.*;

@Api(tags = "소설 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelController {
    private final NovelService novelService;
    private final NovelDetailService novelDetailService;

    // Reader 관련
    @ApiOperation(value = createNovelByReaderValue, notes = createNovelByReaderNotes)
    @PostMapping("/reader")
    public ResponseEntity<NovelDto> createNovelByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                        @RequestPart MultipartFile file,
                                                        @RequestPart String novelName,
                                                        @RequestPart String novelIntroduce) {
        return ResponseEntity.ok(novelService.createNovelByReader(reader, file, novelName, novelIntroduce));
    }

    @ApiOperation(value = modifyNovelByReaderValue, notes = modifyNovelByReaderNotes)
    @PatchMapping("/reader/{novelId}/modify")
    ResponseEntity<NovelDto> modifyNovelByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                 @PathVariable Long novelId,
                                                 @RequestPart(required = false) MultipartFile file,
                                                 @RequestPart(required = false) String novelIntroduce) {
        return ResponseEntity.ok(novelService.modifyNovelByReader(reader, novelId, file, novelIntroduce));
    }

    @ApiOperation(value = deactivateNovelByReaderValue, notes = deactivateNovelByReaderNotes)
    @PatchMapping("/reader/{novelId}/deactivate")
    ResponseEntity<String> deactivateNovelByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                   @PathVariable Long novelId) {
        novelService.deactivateNovelByReader(reader, novelId);
        return ResponseEntity.ok("Deactivated");
    }

    @ApiOperation(value = createNovelDetailByReaderValue, notes = createNovelDetailByReaderNotes)
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

    @ApiOperation(value = modifyNovelDetailByReaderValue, notes = modifyNovelDetailByReaderNotes)
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

    @ApiOperation(value = deactivateNovelDetailByReaderValue, notes = deactivateNovelDetailByReaderNotes)
    @PatchMapping("/reader/{novelId}/{novelDetailId}/deactivate")
    public ResponseEntity<String> deactivateNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                                @PathVariable Long novelId,
                                                                @PathVariable Long novelDetailId) {
        novelDetailService.deactivateNovelDetailByReader(reader, novelId, novelDetailId);
        return ResponseEntity.ok("Deactivated");
    }

    @ApiOperation(value = uploadFileOnNovelDetailByReaderValue, notes = uploadFileOnNovelDetailByReaderNotes)
    @PostMapping("/reader/{novelId}/{novelDetailId}/uploadFile")
    public ResponseEntity<List<String>> uploadFileOnNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                                        @PathVariable Long novelId,
                                                                        @PathVariable Long novelDetailId,
                                                                        @RequestPart MultipartFile[] files,
                                                                        @RequestPart String fileType) {
        return ResponseEntity.ok(novelDetailService.uploadFilesByReader(reader, novelId, novelDetailId, files, fileType));
    }

    @ApiOperation(value = viewListOfOwnNovelByReaderValue, notes = viewListOfOwnNovelByReaderNotes)
    @GetMapping("/reader/list")
    public ResponseEntity<List<NovelDto>> viewOwnNovelListByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                                   @RequestParam("startIdx") long startIdx,
                                                                   @RequestParam("endIdx") long endIdx) {
        return ResponseEntity.ok(novelService.viewOwnNovelListByReader(reader, startIdx, endIdx));
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
    @GetMapping("/view/{novelDetailId}")
    public ResponseEntity<NovelDetailDto> viewNovelDetail(@PathVariable Long novelDetailId) {
        return ResponseEntity.ok(novelDetailService.viewNovelDetail(novelDetailId));
    }

    @ApiOperation(value = viewListOfNewNovelValue, notes = viewListOfNewNovelNotes)
    @GetMapping("/list/new")
    public ResponseEntity<List<NovelDto>> viewListOfNewNovel() {
        return ResponseEntity.ok(novelService.viewListOfNewNovel());
    }

    @ApiOperation(value = viewListOfPopularNovelValue, notes = viewListOfPopularNovelNotes)
    @GetMapping("/list/popular")
    public ResponseEntity<List<NovelDto>> viewListOfPopularNovel(@RequestParam("startIdx") long startIdx,
                                                                 @RequestParam("endIdx") long endIdx) {
        return ResponseEntity.ok(novelService.viewListOfPopularNovel(startIdx, endIdx));
    }

    @ApiOperation(value = viewListOfAuthorNovelValue, notes = viewListOfAuthorNovelNotes)
    @GetMapping("/list/author/{authorId}")
    public ResponseEntity<List<NovelDto>> viewListOfAuthorNovel(@PathVariable Long authorId,
                                                                @RequestParam("type") String publisherType,
                                                                @RequestParam("startIdx") long startIdx,
                                                                @RequestParam("endIdx") long endIdx) {
        return ResponseEntity.ok(novelService.viewListOfAuthorNovel(authorId, publisherType, startIdx, endIdx));
    }

    @GetMapping("/list/search")
    public ResponseEntity<List<NovelDto>> viewListOfKeywordSearchNovel(@RequestParam("keyword") String keyword,
                                                                       @RequestParam("startIdx") long startIdx,
                                                                       @RequestParam("endIdx") long endIdx) {
        return ResponseEntity.ok(novelService.viewListOfKeywordSearch(keyword, startIdx, endIdx));
    }

    @GetMapping("/list/detail/{novelId}")
    public ResponseEntity<List<NovelDetailListDto>> viewListOfNovelDetail(@PathVariable Long novelId,
                                                                      @RequestParam("order") String order,
                                                                      @RequestParam("startIdx") long startIdx,
                                                                      @RequestParam("endIdx") long endIdx) {
        return ResponseEntity.ok(novelDetailService.viewListOfNovelDetail(novelId, order, startIdx, endIdx));
    }

    @ApiOperation(value = viewNovelAverageScoreValue, notes = viewNovelAverageScoreNotes)
    @GetMapping("/score")
    public ResponseEntity<Double> viewNovelAverageScore(@RequestParam("novelId") Long novelId) {
        return ResponseEntity.ok(novelService.viewNovelAverageScore(novelId));
    }
}
