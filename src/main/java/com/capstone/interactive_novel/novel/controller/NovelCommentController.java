package com.capstone.interactive_novel.novel.controller;

import com.capstone.interactive_novel.novel.dto.NovelCommentDto;
import com.capstone.interactive_novel.novel.service.NovelCommentService;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/novel/comment")
public class NovelCommentController {
    private final NovelCommentService novelCommentService;

    @PostMapping("/{novelDetailId}")
    public ResponseEntity<NovelCommentDto> createComment(@AuthenticationPrincipal ReaderEntity reader,
                                                         @PathVariable Long novelDetailId,
                                                         @RequestBody String comment) {
        return ResponseEntity.ok(novelCommentService.createNovelComment(reader, novelDetailId, comment));
    }

    @PatchMapping("/{novelDetailId}/{novelCommentId}/modify")
    public ResponseEntity<NovelCommentDto> modifyComment(@AuthenticationPrincipal ReaderEntity reader,
                                                         @PathVariable Long novelDetailId,
                                                         @PathVariable Long novelCommentId,
                                                         @RequestBody String comment) {
        return ResponseEntity.ok(novelCommentService.modifyNovelComment(reader, novelDetailId, novelCommentId, comment));
    }

    @PatchMapping("/{novelDetailId}/{novelCommentId}/deactivate")
    public ResponseEntity<String> deactivateComment(@AuthenticationPrincipal ReaderEntity reader,
                                                             @PathVariable Long novelDetailId,
                                                             @PathVariable Long novelCommentId) {
        novelCommentService.deactivateNovelComment(reader, novelDetailId, novelCommentId);
        return ResponseEntity.ok("Deactivated");
    }

    @GetMapping("/list/{novelDetailId}")
    public ResponseEntity<List<NovelCommentDto>> viewListOfComments(@PathVariable Long novelDetailId,
                                                                    @RequestParam long startIdx,
                                                                    @RequestParam long endIdx,
                                                                    @RequestParam String method) {
        return ResponseEntity.ok(novelCommentService.viewListOfComment(startIdx, endIdx, novelDetailId, method));
    }
}
