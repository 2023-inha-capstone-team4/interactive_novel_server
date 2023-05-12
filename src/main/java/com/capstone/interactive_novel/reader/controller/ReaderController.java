package com.capstone.interactive_novel.reader.controller;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.dto.ReaderDto;
import com.capstone.interactive_novel.reader.service.ReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;

    @PostMapping("/getAuthor")
    public ResponseEntity<String> applyAuthorRole(@AuthenticationPrincipal ReaderEntity reader) {
        return ResponseEntity.ok(readerService.applyAuthorService(reader));
    }

    @PostMapping("/modifyProfileImg")
    public ResponseEntity<?> modifyProfileImg(@AuthenticationPrincipal ReaderEntity reader,
                                              @RequestPart MultipartFile file) {
        return ResponseEntity.ok(readerService.modifyReaderProfileImg(reader, file));
    }

    @GetMapping("/info")
    public ResponseEntity<ReaderDto.view> viewReaderInfo(@AuthenticationPrincipal ReaderEntity reader) {
        return ResponseEntity.ok(readerService.viewReaderInfo(reader));
    }
}
