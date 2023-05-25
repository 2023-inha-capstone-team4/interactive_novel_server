package com.capstone.interactive_novel.novel.controller;

import com.capstone.interactive_novel.novel.dto.NovelCommentDto;
import com.capstone.interactive_novel.novel.service.NovelCommentService;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.capstone.interactive_novel.common.document.SwaggerDocument.documentAboutNovelCommentController.*;

@Api(tags = "소설 댓글 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/novel/comment")
public class NovelCommentController {
    private final NovelCommentService novelCommentService;

    @ApiOperation(value = createCommentValue, notes = createCommentNotes)
    @PostMapping("/{novelDetailId}")
    public ResponseEntity<NovelCommentDto> createComment(@AuthenticationPrincipal ReaderEntity reader,
                                                         @PathVariable Long novelDetailId,
                                                         @RequestBody String comment) {
        return ResponseEntity.ok(novelCommentService.createNovelComment(reader, novelDetailId, comment));
    }

    @ApiOperation(value = modifyCommentValue, notes = modifyCommentNotes)
    @PatchMapping("/{novelDetailId}/{novelCommentId}/modify")
    public ResponseEntity<NovelCommentDto> modifyComment(@AuthenticationPrincipal ReaderEntity reader,
                                                         @PathVariable Long novelDetailId,
                                                         @PathVariable Long novelCommentId,
                                                         @RequestBody String comment) {
        return ResponseEntity.ok(novelCommentService.modifyNovelComment(reader, novelDetailId, novelCommentId, comment));
    }

    @ApiOperation(value =deactivateCommentValue, notes = deactivateCommentNotes)
    @PatchMapping("/{novelDetailId}/{novelCommentId}/deactivate")
    public ResponseEntity<String> deactivateComment(@AuthenticationPrincipal ReaderEntity reader,
                                                             @PathVariable Long novelDetailId,
                                                             @PathVariable Long novelCommentId) {
        novelCommentService.deactivateNovelComment(reader, novelDetailId, novelCommentId);
        return ResponseEntity.ok("Deactivated");
    }

    @ApiOperation(value = viewListOfCommentValue, notes = viewListOfCommentNotes)
    @GetMapping("/list/{novelDetailId}")
    public ResponseEntity<List<NovelCommentDto>> viewListOfComments(@PathVariable Long novelDetailId,
                                                                    @RequestParam long startIdx,
                                                                    @RequestParam long endIdx,
                                                                    @RequestParam String method) {
        return ResponseEntity.ok(novelCommentService.viewListOfComment(startIdx, endIdx, novelDetailId, method));
    }

    @ApiOperation(value = recommendCommentValue, notes = recommendCommentNotes)
    @PostMapping("/recommend/{novelCommentId}")
    public ResponseEntity<?> recommendComment(@AuthenticationPrincipal ReaderEntity reader,
                                              @PathVariable Long novelCommentId) {
        novelCommentService.sendRecommendCommentMessage(reader, novelCommentId);
        return ResponseEntity.ok(null);
    }
}
