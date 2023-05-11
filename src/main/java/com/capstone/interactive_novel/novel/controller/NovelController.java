package com.capstone.interactive_novel.novel.controller;

import com.capstone.interactive_novel.novel.dto.NovelDetailMediaDto;
import com.capstone.interactive_novel.novel.dto.NovelDto;
import com.capstone.interactive_novel.novel.service.NovelDetailService;
import com.capstone.interactive_novel.novel.service.NovelService;
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
        return ResponseEntity.ok(novelService.deactivateNovelByReader(reader, novelId));
    }

    @PostMapping("/reader/{novelId}")
    public ResponseEntity<?> createNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                       @PathVariable Long novelId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String novelDetailName,
                                                       @RequestPart String novelDetailIntroduce,
                                                       @RequestPart MultipartFile novelScriptFile,
                                                       @RequestPart NovelDetailMediaDto mediaDto) {
        return ResponseEntity.ok(novelDetailService.createNovelDetailByReader(reader, novelId, file, novelDetailName, novelDetailIntroduce, novelScriptFile, mediaDto));
    }

    @PostMapping("/reader/{novelId}/{novelDetailId}/uploadFile")
    public ResponseEntity<List<String>> uploadFileOnNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                                        @PathVariable Long novelId,
                                                                        @PathVariable Long novelDetailId,
                                                                        @RequestPart MultipartFile[] files,
                                                                        @RequestPart String fileType) {
        return ResponseEntity.ok(novelDetailService.uploadFilesByReader(reader, novelId, novelDetailId, files, fileType));
    }
}
