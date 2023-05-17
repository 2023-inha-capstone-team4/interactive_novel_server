package com.capstone.interactive_novel.common.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.common.security.TokenProvider;
import com.capstone.interactive_novel.common.dto.JwtDto;
import com.capstone.interactive_novel.common.dto.RefreshDto;
import com.capstone.interactive_novel.common.components.TokenComponents;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static com.capstone.interactive_novel.common.exception.ErrorCode.FAILED_TO_GET_NAVER_AUTH_TOKEN;
import static com.capstone.interactive_novel.common.exception.ErrorCode.FAILED_TO_GET_NAVER_USER_INFO;
import static com.capstone.interactive_novel.common.type.Role.FREE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final ReaderRepository readerRepository;
    private final TokenProvider tokenProvider;
    private final TokenComponents tokenUtils;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    public JwtDto login(OAuth2User oAuth2User) {
        return tokenProvider.generateOAuthUserToken(oAuth2User);
    }

    public JwtDto naverLogin(String code, String state) {
        ResponseEntity<Map> authTokenResponse = getNaverOAuthAccessToken(code, state);
        if(ObjectUtils.isEmpty(authTokenResponse.getBody())) {
            throw new INovelException(FAILED_TO_GET_NAVER_AUTH_TOKEN);
        }
        String accessToken = authTokenResponse.getBody().get("access_token").toString();

        HttpHeaders userHeader = new HttpHeaders();
        RestTemplate userTemplate = new RestTemplate();

        userHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> getUserInfoRequest = new HttpEntity<>(userHeader);
        ResponseEntity<Map> userResponse = userTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, getUserInfoRequest, Map.class);

        if(ObjectUtils.isEmpty(userResponse)) {
            throw new INovelException(FAILED_TO_GET_NAVER_USER_INFO);
        }

        Optional<ReaderEntity> optionalReader = readerRepository.findByEmail(userResponse.getBody().get("id").toString());
        ReaderEntity reader;
        if(optionalReader.isEmpty()) {
            reader = ReaderEntity.builder()
                    .email(userResponse.getBody().get("id").toString())
                    .interlock("naver")
                    .role(FREE)
                    .authorServiceYn(true)
                    .build();
            readerRepository.save(reader);
        }
        else {
            reader = optionalReader.get();
        }
        return tokenProvider.generateNaverUserToken(reader);
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

        Authentication authentication = tokenProvider.getAuthenticationAboutReader(refreshToken);

        return tokenProvider.generateReaderToken(authentication.getName());
    }

    private ResponseEntity<Map> getNaverOAuthAccessToken(String code, String state) {
        RestTemplate authTokenTemplate = new RestTemplate();

        return authTokenTemplate.getForEntity("https://nid.naver.com/oauth2.0/token?code=" + code +
                                                  "&state=" + state + "&client_id=" + NAVER_CLIENT_ID +
                                                  "&client_secret=" + NAVER_CLIENT_SECRET + "&grant_type=authorization_code", Map.class);
    }
}
