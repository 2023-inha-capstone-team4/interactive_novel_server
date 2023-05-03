package com.capstone.interactive_novel.novel.controller;

import com.capstone.interactive_novel.novel.service.NovelService;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelController {
    private final NovelService novelService;

    @PostMapping("/reader")
    public ResponseEntity<?> createNovelByReader(@AuthenticationPrincipal ReaderEntity reader,
                                                 @RequestPart MultipartFile file,
                                                 @RequestPart String novelName,
                                                 @RequestPart String novelIntroduce) {
        return ResponseEntity.ok(novelService.createNovelByReader(reader, file, novelName, novelIntroduce));
    }
}
