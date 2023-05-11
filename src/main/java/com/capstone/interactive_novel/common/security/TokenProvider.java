package com.capstone.interactive_novel.common.security;

import com.capstone.interactive_novel.common.components.TokenComponents;
import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.publisher.repository.PublisherRepository;
import com.capstone.interactive_novel.publisher.service.PublisherService;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import com.capstone.interactive_novel.reader.service.ReaderService;
import com.capstone.interactive_novel.common.dto.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

import static com.capstone.interactive_novel.common.exception.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    private static final String KEY_ROLE = "role";
    private static final String KEY_USERNAME = "username";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
    private final ReaderService readerService;
    private final PublisherService publisherService;
    private final ReaderRepository readerRepository;
    private final PublisherRepository publisherRepository;
    private final TokenComponents tokenComponents;

    public JwtDto generateReaderToken(String email) {
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmail(email);
        if(optionalReader.isEmpty()) {
            throw new INovelException(USER_NOT_FOUND);
        }
        ReaderEntity reader = optionalReader.get();

        return setJwtDto(email, reader.getUsername(), reader.getRole().getKey());
    }

    public JwtDto generatePublisherToken(String email) {
        Optional<PublisherEntity> optionalPublisher = publisherRepository.findByEmail(email);
        if(optionalPublisher.isEmpty()) {
            throw new INovelException(USER_NOT_FOUND);
        }
        PublisherEntity publisher = optionalPublisher.get();

        return setJwtDto(email, publisher.getUsername(), publisher.getRole().getKey());
    }
    public JwtDto generateOAuthUserToken(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");
        String role = oAuth2User.getAuthorities().toString();

        System.out.println(email + " " + username + " " + role);

        return setJwtDto(email, username, role);
    }

    private JwtDto setJwtDto(String email, String username, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put(KEY_USERNAME, username);
        claims.put(KEY_ROLE, role);

        var now = new Date();
        var accessTokenExpiredTime = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

        var accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiredTime)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
        var refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
        return JwtDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresTime(accessTokenExpiredTime)
                .build();
    }

    public Authentication getAuthenticationAboutReader(String token) {
        UserDetails userDetails = readerService.loadUserByUsername(tokenComponents.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Authentication getAuthenticationAboutPublisher(String token) {
        UserDetails userDetails = publisherService.loadUserByUsername(tokenComponents.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        if(!StringUtils.hasText(token)) return false;

        var claims = tokenComponents.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }
}
