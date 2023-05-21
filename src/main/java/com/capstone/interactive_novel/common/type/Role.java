package com.capstone.interactive_novel.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    FREE("ROLE_FREE", "무료 이용자"),
    PAY("ROLE_PAY", "유료 이용자"),
    UNCERTIFIED("ROLE_UNCERTIFIED", "미인증 이용자"),
    JUNIOR("ROLE_JUNIOR", "초보 작가"),
    PUBLISHER("ROLE_PUBLISHER", "프로 작가"),
    ADMIN("ROLE_ADMIN", "관리자"),
    DEACTIVATED("ROLE_DEACTIVATED", "비활성화 된 사용자");

    private final String key;
    private final String title;
}
