package com.capstone.interactive_novel.publisher.controller;

import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.publisher.dto.PublisherDto;
import com.capstone.interactive_novel.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/publisher")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;
    @PostMapping("/modifyProfileImg")
    public ResponseEntity<?> modifyProfileImg(@AuthenticationPrincipal PublisherEntity publisher,
                                              @RequestPart MultipartFile file) {
        return ResponseEntity.ok(publisherService.modifyPublisherProfileImg(publisher, file));
    }

    @GetMapping("/info")
    public ResponseEntity<PublisherDto.view> viewReaderInfo(@AuthenticationPrincipal PublisherEntity publisher) {
        return ResponseEntity.ok(publisherService.viewPublisherInfo(publisher));
    }
}
