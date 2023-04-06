package com.capstone.interactive_novel.security;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import com.capstone.interactive_novel.reader.service.ReaderService;
import com.capstone.interactive_novel.common.dto.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    private final ReaderRepository readerRepository;

    public JwtDto generateToken(String email) {
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmail(email);
        if(optionalReader.isEmpty()) {
            log.info("존재하지 않는 사용자입니다.");
            return null;
        }
        ReaderEntity reader = optionalReader.get();

        return setJwtDto(email, reader.getUsername(), reader.getRole().getKey());
    }

    public JwtDto generateToken(OAuth2User oAuth2User) {
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

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = readerService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if(!StringUtils.hasText(token)) return false;

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
