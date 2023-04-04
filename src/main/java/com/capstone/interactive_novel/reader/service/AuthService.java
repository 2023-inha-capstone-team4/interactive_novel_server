package com.capstone.interactive_novel.reader.service;

import com.capstone.interactive_novel.security.TokenProvider;
import com.capstone.interactive_novel.security.dto.JwtDto;
import com.capstone.interactive_novel.security.dto.RefreshDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;

    public JwtDto login(OAuth2User oAuth2User) {
        return tokenProvider.generateToken(oAuth2User);
    }

    public JwtDto refresh(RefreshDto refreshDto) {
        boolean result = tokenProvider.validateToken(refreshDto.getRefreshToken());
        if(!result) {
            log.info("유효하지 않은 토큰입니다.");
            return null;
        }
        Authentication authentication = tokenProvider.getAuthentication(refreshDto.getRefreshToken());

        return tokenProvider.generateToken(authentication.getName());
    }
}
