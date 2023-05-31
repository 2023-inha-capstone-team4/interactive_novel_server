package com.capstone.interactive_novel.notification.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    BOOKMARKED_NOVEL_CREATED_NEW_TURN("북마크한 소설의 새로운 회차가 게시되었습니다."),
    BOOKMARKED_USER_CREATED_NEW_NOVEL("북마크한 유저의 새로운 소설이 게시되었습니다."),
    BOOKMARKED_USER_CREATED_NEW_NOVEL_TURN("북마크한 유저의 소설의 새로운 회차가 게시되었습니다.");

    private final String message;
}
