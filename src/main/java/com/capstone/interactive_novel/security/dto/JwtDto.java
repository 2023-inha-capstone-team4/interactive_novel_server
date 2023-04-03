package com.capstone.interactive_novel.security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JwtDto {
    String grantType;
    String accessToken;
    String refreshToken;
    Date accessTokenExpiresTime;
}
