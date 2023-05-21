package com.capstone.interactive_novel.novel.controller;

import com.capstone.interactive_novel.novel.dto.NovelReviewDto;
import com.capstone.interactive_novel.novel.dto.NovelReviewInputDto;
import com.capstone.interactive_novel.novel.service.NovelReviewService;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/novel/review")
public class NovelReviewController {
    private final NovelReviewService novelReviewService;

    @PostMapping("/{novelId}")
    public ResponseEntity<NovelReviewDto> createNovelReview(@AuthenticationPrincipal ReaderEntity reader,
                                                            @PathVariable Long novelId,
                                                            @RequestBody NovelReviewInputDto novelReviewInputDto) {
        return ResponseEntity.ok(novelReviewService.createNovelReview(reader, novelId, novelReviewInputDto.getReview(), novelReviewInputDto.getNovelScore()));
    }

    @PatchMapping("/{novelId}/{novelReviewId}/deactivate")
    public ResponseEntity<String> deactivateNovelReview(@AuthenticationPrincipal ReaderEntity reader,
                                                        @PathVariable Long novelId,
                                                        @PathVariable Long novelReviewId) {
        novelReviewService.deactivateNovelReview(reader, novelId, novelReviewId);
        return ResponseEntity.ok("Deactivated");
    }

    @GetMapping("/list/{novelId}")
    public ResponseEntity<List<NovelReviewDto>> viewListOfNewNovelReview(@PathVariable Long novelId,
                                                                         @RequestParam String method,
                                                                         @RequestParam String order,
                                                                         @RequestParam Long startIdx,
                                                                         @RequestParam Long endIdx) {
        return ResponseEntity.ok(novelReviewService.viewListOfNewNovelReview(startIdx, endIdx, novelId, method, order));
    }
}
