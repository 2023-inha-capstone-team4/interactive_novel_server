package com.capstone.interactive_novel.common.controller;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.model.ReaderModel;
import com.capstone.interactive_novel.common.service.AuthService;
import com.capstone.interactive_novel.reader.service.ReaderService;
import com.capstone.interactive_novel.common.security.TokenProvider;
import com.capstone.interactive_novel.common.dto.JwtDto;
import com.capstone.interactive_novel.common.dto.RefreshDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final ReaderService readerService;
    private final AuthService authService;
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

        var token = tokenProvider.generateToken(reader.getEmail());
        if(ObjectUtils.isEmpty(token)) {
            return ResponseEntity.ok("로그인에 실패하였습니다.");
        }
        return ResponseEntity.ok("로그인에 성공하였습니다.\n" + token);
    }

    @GetMapping("/sign/in/oauth2")
    public ResponseEntity<JwtDto> oAuthSignIn(@AuthenticationPrincipal OAuth2User oAuthUser) {
        return ResponseEntity.ok(authService.login(oAuthUser));
    }

    @GetMapping("/sign/email-auth")
    public ResponseEntity<?> emailAuth(HttpServletRequest request) {
        String uuid = request.getParameter("id");
        boolean result = readerService.emailAuth(uuid);
        return result ?
                ResponseEntity.ok("이메일 인증에 성공하였습니다.") :
                ResponseEntity.ok("이메일 인증에 실패하였습니다.");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> tokenRefreshRequest(@RequestHeader("Authorization") String token,
                                                 @RequestBody RefreshDto refreshDto) {
        var result = authService.refresh(refreshDto, token);
        return !ObjectUtils.isEmpty(result) ?
                ResponseEntity.ok("토큰 재발급에 성공하였습니다.\n" + result) :
                ResponseEntity.ok("토큰 재발급에 실패하였습니다.");
    }
}
