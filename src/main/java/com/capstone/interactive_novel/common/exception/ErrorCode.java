package com.capstone.interactive_novel.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 기본 에러
    AMAZON_S3_ERROR("AMAZON S3 관련 오류가 발생했습니다."),
    DESERIALIZER_ERROR("역직렬화 관련 오류가 발생했습니다."),
    INTERNAL_ERROR("내부 서버 오류가 발생했습니다."),
    JWT_ERROR("JWT 토큰 관련 오류가 발생했습니다."),
    SERIALIZER_ERROR("직렬화 관련 오류가 발생했습니다."),

    // 중복 에러
    ALREADY_VERIFIED_EMAIL("이미 인증된 이메일입니다."),
    ALREADY_VERIFIED_USER("이미 인증된 사용자입니다."),
    ALREADY_USING_EMAIL("이미 사용 중인 이메일입니다."),
    ALREADY_USING_NOVEL_NAME("이미 사용 중인 소설명입니다."),

    // 수행 불가 에러
    CANNOT_BOOKMARK_CORRESPONDING_USER("해당 유저는 북마크할 수 없습니다."),
    CANNOT_BOOKMARK_OWN_NOVEL("자신의 소설은 북마크할 수 없습니다."),
    CANNOT_BOOKMARK_YOURSELF("자기 자신은 북마크할 수 없습니다."),
    CANNOT_RECOMMEND_CORRESPONDING_COMMENT("해당 댓글은 추천할 수 없습니다."),
    CANNOT_RECOMMEND_OWN_COMMENT("자기 자신의 댓글은 추천할 수 없습니다."),
    CANNOT_REVIEW_OWN_NOVEL("자신의 소설에는 리뷰를 남길 수 없습니다."),
    CANNOT_REVIEW_EXCESS_ONE("리뷰는 하나만 등록할 수 있습니다."),
    CANNOT_INIT_FIREBASE("Firebase를 초기화할 수 없습니다."),

    // 탐색 불가 에러
    AUTH_KEY_NOT_FOUND("일치하는 인증 키를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND("해당하는 이메일을 찾을 수 없습니다."),
    NOVEL_NOT_FOUND("해당하는 소설을 찾을 수 없습니다."),
    NOVEL_BOOKMARK_LIST_NOT_FOUND("북마크 한 소설을 찾을 수 없습니다."),
    NOVEL_COMMENT_NOT_FOUND("해당하는 소설 댓글을 찾을 수 없습니다."),
    NOVEL_DETAIL_NOT_FOUND("해당하는 소설 회차를 찾을 수 없습니다."),
    NOVEL_REVIEW_NOT_FOUND("해당하는 소설 리뷰를 찾을 수 없습니다."),
    PUBLISHER_BOOKMARK_LIST_NOT_FOUND("북마크 한 작가를 찾을 수 없습니다."),
    READER_BOOKMARK_LIST_NOT_FOUND("북마크 한 독자를 찾을 수 없습니다"),
    TOKEN_NOT_FOUND("액세스 토큰을 찾을 수 없습니다."),
    USER_NOT_FOUND("해당하는 사용자를 찾을 수 없습니다."),

    // 실패 에러
    FAILED_TO_GET_NAVER_AUTH_TOKEN("네이버 인증 토큰을 불러오는 데 실패하였습니다."),
    FAILED_TO_GET_NAVER_USER_INFO("네이버 유저 정보를 불러오는 데 실패하였습니다."),
    FAILED_TO_WRITE_KAFKA_MESSAGE_LOG("Kafka 오류 메시지 로그 작성에 실패하였습니다."),

    // 유효성 에러
    INVALID_ACCESS_TOKEN("액세스 토큰이 유효하지 않습니다."),
    INVALID_FILE_TYPE("유효하지 않은 파일 타입입니다."),
    INVALID_PARAMETER_VALUE("유효하지 않은 파라미터 값입니다."),
    INVALID_PAY_TYPE("유효하지 않은 요금제 방식입니다."),
    INVALID_USER_AUTHENTICATION("인증 정보가 유효하지 않습니다."),

    // 불일치 에러
    UNMATCHED_COMMENT_INFO("댓글 정보가 일치하지 않습니다."),
    UNMATCHED_NOVEL_INFO("소설 정보가 일치하지 않습니다."),
    UNMATCHED_NOVEL_REVIEW_INFO("소설 리뷰 정보가 일치하지 않습니다."),
    UNMATCHED_PASSWORD("비밀번호가 일치하지 않습니다."),
    UNMATCHED_USER_INFO("사용자 정보가 일치하지 않습니다."),
    // 불필요한 요청

    // 인증 에러
    UNVERIFIED_EMAIL("인증되지 않은 이메일입니다."),
    UNVERIFIED_USER("인증되지 않은 사용자입니다."),

    // 잘못된 정보 에러
    WRONG_FILE_EXTENSION("파일 확장자가 잘못되었습니다."),
    WRONG_METHOD_OR_URL("요청하신 메서드 혹은 주소가 잘못되었습니다."),
    WRONG_NOVEL_SCORE_VALUE("소설 점수 값이 잘못되었습니다.");

    private final String description;
}
