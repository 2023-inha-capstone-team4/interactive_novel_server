package com.capstone.interactive_novel.reader.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReaderRole {
    FREE("ROLE_FREE", "무료 이용자"),
    PAY("ROLE_PAY", "유료 이용자");

    private final String key;
    private final String title;
}
