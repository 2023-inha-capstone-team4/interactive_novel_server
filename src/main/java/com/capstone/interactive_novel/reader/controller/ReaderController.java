package com.capstone.interactive_novel.reader.controller;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.dto.ReaderDto;
import com.capstone.interactive_novel.reader.service.ReaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.capstone.interactive_novel.common.document.SwaggerDocument.documentAboutReaderController.*;

@Slf4j
@Api(tags = "독자 관련 API")
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;

    @ApiOperation(value = getAuthorRoleValue, notes = getAuthorRoleNotes)
    @PostMapping("/getAuthor")
    public ResponseEntity<?> applyAuthorRole(@AuthenticationPrincipal ReaderEntity reader) {
        return ResponseEntity.ok(readerService.applyAuthorService(null));
    }

    @ApiOperation(value = modifyProfileImgValue, notes = modifyProfileImgNotes)
    @PostMapping("/modifyProfileImg")
    public ResponseEntity<?> modifyProfileImg(@AuthenticationPrincipal ReaderEntity reader,
                                              @RequestPart MultipartFile file) {
        return ResponseEntity.ok(readerService.modifyReaderProfileImg(reader, file));
    }

    @GetMapping("/info")@ApiOperation(value = viewReaderInfoValue, notes = viewReaderInfoNotes)
    public ResponseEntity<ReaderDto.view> viewReaderInfo(@AuthenticationPrincipal ReaderEntity reader) {
        return ResponseEntity.ok(readerService.viewReaderInfo(reader));
    }
}
