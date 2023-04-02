package com.capstone.interactive_novel.reader.controller;

import com.capstone.interactive_novel.reader.model.ReaderModel;
import com.capstone.interactive_novel.reader.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;
    @PostMapping("/sign/up/reader")
    public ResponseEntity<?> readerSignUp(@RequestBody ReaderModel.SignUp parameter) {
        boolean result = readerService.register(parameter);
        return result ?
                ResponseEntity.ok("회원 가입이 완료되었습니다.") :
                ResponseEntity.ok("회원 가입에 실패하였습니다.");
    }
}
