package com.capstone.interactive_novel.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    FREE("ROLE_FREE", "무료 이용자"),
    PAY("ROLE_PAY", "유료 이용자"),
    UNCERTIFIED("ROLE_UNCERTIFIED", "미인증 이용자"),
    PUBLISHER("ROLE_PUBLISHER", "작가"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
