package com.capstone.interactive_novel.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 기본 에러
    AMAZON_S3_ERROR("AMAZON S3 관련 오류가 발생했습니다."),
    INTERNAL_ERROR("내부 서버 오류가 발생했습니다."),
    JWT_ERROR("JWT 토큰 관련 오류가 발생했습니다."),

    // 중복 에러
    ALREADY_VERIFIED_EMAIL("이미 인증된 이메일입니다."),
    ALREADY_VERIFIED_USER("이미 인증된 사용자입니다."),
    ALREADY_USING_EMAIL("이미 사용 중인 이메일입니다."),
    ALREADY_USING_NOVEL_NAME("이미 사용 중인 소설명입니다."),

    // 수행 불가 에러
    CANNOT_BOOKMARK_OWN_NOVEL("자신의 소설은 북마크할 수 없습니다."),
    CANNOT_BOOKMARK_YOURSELF("자기 자신은 북마크할 수 없습니다."),

    // 탐색 불가 에러
    AUTH_KEY_NOT_FOUND("일치하는 인증 키를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND("해당하는 이메일을 찾을 수 없습니다."),
    NOVEL_NOT_FOUND("해당하는 소설을 찾을 수 없습니다."),
    NOVEL_BOOKMARK_LIST_NOT_FOUND("북마크 한 소설을 찾을 수 없습니다."),
    NOVEL_DETAIL_NOT_FOUND("해당하는 소설 회차를 찾을 수 없습니다."),
    READER_BOOKMARK_LIST_NOT_FOUND("북마크 한 독자를 찾을 수 없습니다"),
    TOKEN_NOT_FOUND("액세스 토큰을 찾을 수 없습니다."),
    USER_NOT_FOUND("해당하는 사용자를 찾을 수 없습니다."),

    // 유효성 에러
    INVALID_ACCESS_TOKEN("액세스 토큰이 유효하지 않습니다."),
    INVALID_FILE_TYPE("유효하지 않은 파일 타입입니다."),

    // 불일치 에러
    UNMATCHED_NOVEL_INFO("소설 정보가 일치하지 않습니다."),
    UNMATCHED_PASSWORD("비밀번호가 일치하지 않습니다."),
    UNMATCHED_USER_INFO("사용자 정보가 일치하지 않습니다."),
    // 불필요한 요청

    // 인증 에러
    UNVERIFIED_EMAIL("인증되지 않은 이메일입니다."),
    UNVERIFIED_USER("인증되지 않은 사용자입니다."),

    // 잘못된 정보 에러
    WRONG_FILE_EXTENSION("파일 확장자가 잘못되었습니다."),
    WRONG_METHOD_OR_URL("요청하신 메서드 혹은 주소가 잘못되었습니다.");

    private final String description;
}
