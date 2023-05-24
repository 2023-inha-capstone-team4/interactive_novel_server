package com.capstone.interactive_novel.common.security;

import com.capstone.interactive_novel.common.components.FilterUrlComponents;
import com.capstone.interactive_novel.common.components.TokenComponents;
import com.capstone.interactive_novel.common.dto.ErrorResponseDto;
import com.capstone.interactive_novel.common.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;
import static com.capstone.interactive_novel.common.type.Role.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    private final TokenComponents tokenComponents;
    private final FilterUrlComponents filterUrlComponents;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(checkUrl(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if(ObjectUtils.isEmpty(request.getHeader(TOKEN_HEADER))) {
            setErrorResponse(response, TOKEN_NOT_FOUND);
            return;
        }

        String token = TokenComponents.removeTokenHeader(request.getHeader(TOKEN_HEADER), TOKEN_PREFIX);
        if(!tokenProvider.validateToken(token)) {
            setErrorResponse(response, INVALID_ACCESS_TOKEN);
            return;
        }

        String role = tokenComponents.parseClaims(token).get("role").toString();
        Authentication auth;
        log.info(role);
        if(role.contains(FREE.getKey()) || role.contains(JUNIOR.getKey())) {
            auth = tokenProvider.getAuthenticationAboutReader(token);
        }
        else if(role.contains(PUBLISHER.getKey())) {
            auth = tokenProvider.getAuthenticationAboutPublisher(token);
        }
        else if(role.contains(UNCERTIFIED.getKey())) {
            setErrorResponse(response, UNVERIFIED_EMAIL);
            return;
        }
        else {
            setErrorResponse(response, INVALID_USER_AUTHENTICATION);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info("[TOKEN] 만료 시간 전 정상 처리");
        filterChain.doFilter(request, response);
    }

    private boolean checkUrl(HttpServletRequest request) {
        Set<String> filterUrlSet = filterUrlComponents.filterUrlSet();

        return filterUrlSet.stream()
                .anyMatch(request.getServletPath()::startsWith);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(500);
        response.setContentType("application/json;charset=UTF-8");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorCode, errorCode.getDescription());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponseDto));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
