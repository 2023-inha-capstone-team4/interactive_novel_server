package com.capstone.interactive_novel.novel.controller;

import com.capstone.interactive_novel.novel.service.NovelDetailService;
import com.capstone.interactive_novel.novel.service.NovelService;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelController {
    private final NovelService novelService;
    private final NovelDetailService novelDetailService;

    @PostMapping("/reader")
    public ResponseEntity<?> createNovelByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                 @RequestPart MultipartFile file,
                                                 @RequestPart String novelName,
                                                 @RequestPart String novelIntroduce) {
        return ResponseEntity.ok(novelService.createNovelByReader(reader, file, novelName, novelIntroduce));
    }

    @PostMapping("/reader/{novelId}")
    public ResponseEntity<?> createNovelDetailByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                       @PathVariable Long novelId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String novelDetailName,
                                                       @RequestPart String novelDetailIntroduce,
                                                       @RequestPart MultipartFile novelScriptFile) {
        return ResponseEntity.ok(novelDetailService.createNovelDetailByReader(reader, novelId, file, novelDetailName, novelDetailIntroduce, novelScriptFile));
    }
}
