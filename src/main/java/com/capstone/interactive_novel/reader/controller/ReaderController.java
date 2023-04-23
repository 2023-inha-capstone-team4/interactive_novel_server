package com.capstone.interactive_novel.reader.controller;

import com.capstone.interactive_novel.reader.service.ReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;

    @PostMapping("/getAuthor")
    public ResponseEntity<?> applyAuthorRole(@RequestHeader("Authorization") String token) {
        boolean result = readerService.applyAuthorService(token);
        return result ?
                ResponseEntity.ok("권한 부여가 완료되었습니다.") :
                ResponseEntity.ok("권한 부여가 실패되었습니다.");
    }

    @PostMapping("/modifyProfileImg")
    public ResponseEntity<?> modifyProfileImg(@RequestHeader("Authorization") String token,
                                              @RequestPart MultipartFile file,
                                              @RequestPart String domain) {
        return ResponseEntity.ok(readerService.modifyProfileImg(token, file, domain));
    }
}
