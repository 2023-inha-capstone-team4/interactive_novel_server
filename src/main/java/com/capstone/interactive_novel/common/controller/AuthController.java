package com.capstone.interactive_novel.common.controller;

import com.capstone.interactive_novel.common.security.TokenProvider;
import com.capstone.interactive_novel.publisher.dto.PublisherDto;
import com.capstone.interactive_novel.publisher.service.PublisherService;
import com.capstone.interactive_novel.reader.dto.ReaderDto;
import com.capstone.interactive_novel.common.service.AuthService;
import com.capstone.interactive_novel.reader.service.ReaderService;
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
    private final PublisherService publisherService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/sign/up/reader")
    public ResponseEntity<?> readerSignUp(@RequestBody ReaderDto.SignUp parameter) {
        readerService.register(parameter);
        return ResponseEntity.ok(parameter.getEmail());
    }

    @PostMapping("/sign/in/reader")
    public ResponseEntity<JwtDto> readerSignIn(@RequestBody ReaderDto.SignIn parameter) {
        return ResponseEntity.ok(tokenProvider.generateReaderToken(readerService.login(parameter)));
    }

    @PostMapping("/sign/up/publisher")
    public ResponseEntity<?> publisherSignUp(@RequestBody PublisherDto.SignUp parameter) {
        publisherService.register(parameter);
        return ResponseEntity.ok(parameter.getEmail());
    }

    @PostMapping("/sign/in/publisher")
    public ResponseEntity<JwtDto> publisherSignIn(@RequestBody PublisherDto.SignIn parameter) {
        return ResponseEntity.ok(tokenProvider.generatePublisherToken(publisherService.login(parameter)));
    }

    @GetMapping("/sign/in/oauth2/naver")
    public ResponseEntity<?> naverSignIn(@RequestParam("code") String code,
                                         @RequestParam("state") String state) {
        return ResponseEntity.ok(authService.naverLogin(code, state));
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
