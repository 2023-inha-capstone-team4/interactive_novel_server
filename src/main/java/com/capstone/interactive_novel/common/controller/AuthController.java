package com.capstone.interactive_novel.common.controller;

import com.capstone.interactive_novel.common.security.TokenProvider;
import com.capstone.interactive_novel.publisher.dto.PublisherDto;
import com.capstone.interactive_novel.publisher.service.PublisherService;
import com.capstone.interactive_novel.reader.dto.ReaderDto;
import com.capstone.interactive_novel.common.service.AuthService;
import com.capstone.interactive_novel.reader.service.ReaderService;
import com.capstone.interactive_novel.common.dto.JwtDto;
import com.capstone.interactive_novel.common.dto.RefreshDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.capstone.interactive_novel.common.document.SwaggerDocument.documentAboutAuthController.*;

@Slf4j
@Api(tags = "유저 인증 API")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final ReaderService readerService;
    private final PublisherService publisherService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = readerSignUpValue, notes = readerSignUpNotes)
    @PostMapping("/sign/up/reader")
    public ResponseEntity<?> readerSignUp(@RequestBody ReaderDto.SignUp parameter) {
        readerService.register(parameter);
        return ResponseEntity.ok(parameter.getEmail());
    }

    @ApiOperation(value = readerSignInValue, notes = readerSignInNotes)
    @PostMapping("/sign/in/reader")
    public ResponseEntity<JwtDto> readerSignIn(@RequestBody ReaderDto.SignIn parameter) {
        JwtDto token = tokenProvider.generateReaderToken(readerService.login(parameter));
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
                .header("X-Refresh-Token", token.getGrantType() + " " + token.getRefreshToken())
                .build();

    }

    @ApiOperation(value = publisherSignUpValue, notes = publisherSignUpNotes)
    @PostMapping("/sign/up/publisher")
    public ResponseEntity<?> publisherSignUp(@RequestBody PublisherDto.SignUp parameter) {
        publisherService.register(parameter);
        return ResponseEntity.ok(parameter.getEmail());
    }

    @ApiOperation(value = publisherSignInValue, notes = publisherSignInNotes)
    @PostMapping("/sign/in/publisher")
    public ResponseEntity<JwtDto> publisherSignIn(@RequestBody PublisherDto.SignIn parameter) {
        JwtDto token = tokenProvider.generatePublisherToken(publisherService.login(parameter));
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
                .header("X-Refresh-Token", token.getGrantType() + " " + token.getRefreshToken())
                .build();
    }

    @ApiOperation(value = naverSignInValue, notes = naverSignInNotes)
    @GetMapping("/sign/in/oauth2/naver")
    public ResponseEntity<?> naverSignIn(@RequestParam("code") String code,
                                         @RequestParam("state") String state) {
        JwtDto token = authService.naverLogin(code, state);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token.getGrantType() + " " + token.getAccessToken())
                .header("X-Refresh-Token", token.getGrantType() + " " + token.getRefreshToken())
                .build();
    }

    @GetMapping("/sign/in/oauth2")
    public ResponseEntity<JwtDto> oAuthSignIn(@AuthenticationPrincipal OAuth2User oAuthUser) {
        return ResponseEntity.ok(authService.login(oAuthUser));
    }

    @ApiOperation(value = emailAuthValue, notes = emailAuthNotes)
    @GetMapping("/sign/email-auth")
    public ResponseEntity<?> emailAuth(HttpServletRequest request) {
        String uuid = request.getParameter("id");
        readerService.emailAuth(uuid);
        return ResponseEntity.ok(null);
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
