package com.capstone.interactive_novel.common.service;

import com.capstone.interactive_novel.common.security.TokenProvider;
import com.capstone.interactive_novel.common.dto.JwtDto;
import com.capstone.interactive_novel.common.dto.RefreshDto;
import com.capstone.interactive_novel.common.components.TokenComponents;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final ReaderRepository readerRepository;
    private final TokenProvider tokenProvider;
    private final TokenComponents tokenUtils;

    public JwtDto login(OAuth2User oAuth2User) {
        return tokenProvider.generateToken(oAuth2User);
    }

    public JwtDto refresh(RefreshDto refreshDto, String token) {
        token = TokenComponents.removeTokenHeader(token, "Bearer");
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmail(tokenUtils.getEmail(token));
        if(optionalReader.isEmpty()) {
            log.info("유효하지 않은 사용자입니다.");
            return null;
        }
        String refreshToken = refreshDto.getRefreshToken();
        refreshToken = TokenComponents.removeTokenHeader(refreshToken, "Bearer ");
        boolean result = tokenProvider.validateToken(refreshToken);
        if(!result) {
            log.info("유효하지 않은 토큰입니다.");
            return null;
        }

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);

        return tokenProvider.generateToken(authentication.getName());
    }
}
