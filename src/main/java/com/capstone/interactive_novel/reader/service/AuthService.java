package com.capstone.interactive_novel.reader.service;

import com.capstone.interactive_novel.security.TokenProvider;
import com.capstone.interactive_novel.security.dto.JwtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;

    public JwtDto login(OAuth2User oAuth2User) {
        return tokenProvider.generateToken(oAuth2User);
    }
}
