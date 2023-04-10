package com.capstone.interactive_novel.common.security;

import com.capstone.interactive_novel.common.components.TokenComponents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(checkUrl(request, "/sign/in/oauth2") ||
           checkUrl(request, "/sign/up/reader") ||
           checkUrl(request, "/sign/in/reader") ||
           checkUrl(request, "/sign/email-auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = TokenComponents.removeTokenHeader(request.getHeader(TOKEN_HEADER), TOKEN_PREFIX);

        if(StringUtils.hasText(token) && this.tokenProvider.validateToken(token)) {
            Authentication auth = this.tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            log.info("[TOKEN] 만료 시간 전 정상 처리");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("[TOKEN] 만료 된 토큰 - 미처리");
    }

    private boolean checkUrl(HttpServletRequest request, String url) {
        return request.getRequestURL().toString().contains(url);
    }
}
