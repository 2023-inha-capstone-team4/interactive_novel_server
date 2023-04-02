package com.capstone.interactive_novel.reader.controller;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.model.ReaderModel;
import com.capstone.interactive_novel.reader.service.ReaderService;
import com.capstone.interactive_novel.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;
    private final TokenProvider tokenProvider;

    @PostMapping("/sign/up/reader")
    public ResponseEntity<?> readerSignUp(@RequestBody ReaderModel.SignUp parameter) {
        boolean result = readerService.register(parameter);
        return result ?
                ResponseEntity.ok("회원 가입이 완료되었습니다.") :
                ResponseEntity.ok("회원 가입에 실패하였습니다.");
    }

    @GetMapping("/sign/in/reader")
    public ResponseEntity<?> readerSignIn(@RequestBody ReaderModel.SignIn parameter) {
        ReaderEntity reader = readerService.login(parameter);
        if(ObjectUtils.isEmpty(reader)) {
            return ResponseEntity.ok("로그인에 실패하였습니다.\n");
        }

        var token = tokenProvider.generateToken(reader.getEmail(), reader.getUsername(), reader.getRole().getKey());
        return ResponseEntity.ok("로그인에 성공하였습니다.\n" + token);
    }

    @GetMapping("/sign/email-auth")
    public ResponseEntity<?> emailAuth(HttpServletRequest request) {
        String uuid = request.getParameter("id");
        boolean result = readerService.emailAuth(uuid);
        return result ?
                ResponseEntity.ok("이메일 인증에 성공하였습니다.") :
                ResponseEntity.ok("이메일 인증에 실패하였습니다.");
    }
}
