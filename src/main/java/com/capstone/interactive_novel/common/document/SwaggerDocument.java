package com.capstone.interactive_novel.common.document;

public class SwaggerDocument {
    public static class documentAboutAuthController {
        // readerSignUp
        public static final String readerSignUpValue = "READER 회원가입";
        public static final String readerSignUpNotes = "<b>이메일</b>과 <b>비밀번호</b>, <b>유저 이름(닉네임)</b>을 통해 READER로서 회원가입. <br>추가로 이메일 인증이 필요하다. <br>성공 시 가입 <b>이메일</b>이 반환된다.";

        // readerSignIn
        public static final String readerSignInValue = "READER 로그인";
        public static final String readerSignInNotes = "<b>이메일</b>과 <b>비밀번호</b>로 로그인을 시도. <br>성공 시 <b>JWT Access Token</b>과 <b>JWT Refresh Token</b>을 <u>헤더</u>로 전달한다.";

        // publisherSignUp
        public static final String publisherSignUpValue = "PUBLISHER 회원가입";
        public static final String publisherSignUpNotes = "<b>이메일</b>과 <b>비밀번호</b>, <b>유저 이름(닉네임)</b>을 통해 PUBLISHER로서 회원가입. <br><u>임시 제작 API</u>로, 이후에는 관리자 권한을 가진 사람만 생성 가능하도록 수정할 예정. <br>이메일 인증이 별도로 필요하지 않다.<br>성공 시 <b>이메일</b>이 반환된다.";

        // publisherSignIn
        public static final String publisherSignInValue = "PUBLISHER 로그인";
        public static final String publisherSignInNotes = "<b>이메일</b>과 <b>비밀번호</b>로 로그인을 시도. <br>성공 시 <b>JWT Access Token</b>과 <b>JWT Refresh Token</b>을 <u>헤더</u>로 전달한다.";

        // naverSignIn
        public static final String naverSignInValue = "NAVER OAuth 2.0 로그인";
        public static final String naverSignInNotes = "네이버 OAuth 2.0 로그인 API를 통해 <b>authorization_code</b>와 <b>state</b>값을 얻어 전달함으로써 로그인. <br>성공 시 <b>JWT Access Token</b>과 <b>JWT Refresh Token</b>을 <u>헤더</u>로 전달한다.";

        // oauthLocalSignIn

        // emailAuth
        public static final String emailAuthValue = "이메일 인증";
        public static final String emailAuthNotes = "회원가입 시 발급된 <b>UUID값</b>을 대조해 이메일 인증을 진행. <br>직접 호출하는 API가 아님. <br>성공 시 별도의 반환값은 없다.";

        // tokenRefreshRequest
    }
}
