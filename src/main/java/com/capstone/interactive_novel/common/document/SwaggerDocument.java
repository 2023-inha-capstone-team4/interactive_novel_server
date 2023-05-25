package com.capstone.interactive_novel.common.document;

public class SwaggerDocument {
    public static class documentAboutAuthController {
        // readerSignUp
        public static final String readerSignUpValue = "READER 회원가입";
        public static final String readerSignUpNotes = "<b>이메일</b>과 <b>비밀번호</b>, <b>유저 이름(닉네임)</b>을 통해 READER로서 회원가입. <br>추가로 이메일 인증이 필요하다. <br>성공 시 가입 <b>이메일</b>이 반환된다.";

        // readerSignIn
        public static final String readerSignInValue = "READER 로그인";
        public static final String readerSignInNotes = "<b>이메일</b>과 <b>비밀번호</b>로 로그인을 시도. <br>성공 시 <b>JWT Access Token</b>과 <b>JWT Refresh Token</b>을 <u>header</u>로 전달한다.";

        // publisherSignUp
        public static final String publisherSignUpValue = "PUBLISHER 회원가입";
        public static final String publisherSignUpNotes = "<b>이메일</b>과 <b>비밀번호</b>, <b>유저 이름(닉네임)</b>을 통해 PUBLISHER로서 회원가입. <br><u>임시 제작 API</u>로, 이후에는 관리자 권한을 가진 사람만 생성 가능하도록 수정할 예정. <br>이메일 인증이 별도로 필요하지 않다.<br>성공 시 <b>이메일</b>이 반환된다.";

        // publisherSignIn
        public static final String publisherSignInValue = "PUBLISHER 로그인";
        public static final String publisherSignInNotes = "<b>이메일</b>과 <b>비밀번호</b>로 로그인을 시도. <br>성공 시 <b>JWT Access Token</b>과 <b>JWT Refresh Token</b>을 <u>header</u>로 전달한다.";

        // naverSignIn
        public static final String naverSignInValue = "NAVER OAuth 2.0 로그인";
        public static final String naverSignInNotes = "네이버 OAuth 2.0 로그인 API를 통해 <b>authorization_code</b>와 <b>state</b>값을 얻어 전달함으로써 로그인. <br>성공 시 <b>JWT Access Token</b>과 <b>JWT Refresh Token</b>을 <u>header</u>로 전달한다.";

        // oauthLocalSignIn

        // emailAuth
        public static final String emailAuthValue = "이메일 인증";
        public static final String emailAuthNotes = "회원가입 시 발급된 <b>UUID값</b>을 대조해 이메일 인증을 진행. <br>직접 호출하는 API가 아님. <br>성공 시 별도의 반환값은 없다.";

        // tokenRefreshRequest
    }

    public static class documentAboutReaderController {
        // getAuthorRole
        public static final String getAuthorRoleValue = "아마추어 작가 신청";
        public static final String getAuthorRoleNotes = "작가 기능을 인증하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>성공 시 별도의 반환값은 없다.";
        
        // modifyProfileImg
        public static final String modifyProfileImgValue = "프로필 이미지 변경";
        public static final String modifyProfileImgNotes = "프로필 이미지를 변경하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br> 성공 시 해당 유저 정보가 반환된다.";
        
        // viewReaderInfo
        public static final String viewReaderInfoValue = "유저 정보 보기";
        public static final String viewReaderInfoNotes = "본인의 유저 정보를 확인하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br> 성공 시 해당 유저 정보가 반환된다.";
    }

    public static class documentAboutNovelController {
        // createNovelByReader
        public static final String createNovelByReaderValue = "READER 소설 작성";
        public static final String createNovelByReaderNotes = "READER 권한의 사용자가 소설을 작성하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>추가적으로 <b>소설 대표 이미지</b>, <b>소설 제목</b>, <b>소설 소개글</b>을 <u>form-data</u>로 전달한다. <br>소설 제목은 중복될 수 없다. <br>성공 시 소설 정보를 반환한다.";

        // modifyNovelByReader
        public static final String modifyNovelByReaderValue = "READER 소설 편집";
        public static final String modifyNovelByReaderNotes = "READER 권한의 사용자가 소설을 편집하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>추가적으로 <b>소설 대표 이미지</b>, <b>소설 소개글</b>을 <u>form-data</u>로 전달한다. <br>소설 제목은 수정할 수 없다. <br>성공 시 소설 정보를 반환한다.";

        // deactivateNovelByReader
        public static final String deactivateNovelByReaderValue = "READER 소설 비활성화";
        public static final String deactivateNovelByReaderNotes = "READER 권한의 사용자가 소설을 비활성화하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>해당 소설과, 소설의 모든 회차를 비활성화한다. <br>성공 시 'Deactivated'를 반환한다.";

        // createNovelDetailByReader
        public static final String createNovelDetailByReaderValue = "READER 소설 회차 작성";
        public static final String createNovelDetailByReaderNotes = "READER 권한의 사용자가 소설 회차를 작성하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>추가적으로 <b>소설 회차 대표 이미지</b>, <b>소설 회차 제목</b>, <b>소설 회차 소개</b>, <b>소설 데이터 파일</b>, <b>소설 미디어 파일 리스트</b>를 전달한다. <br>성공 시 소설 회차 정보를 반환한다.";

        // modifyNovelDetailByReader
        public static final String modifyNovelDetailByReaderValue = "READER 소설 회차 편집";
        public static final String modifyNovelDetailByReaderNotes = "READER 권한의 사용자가 소설 회차를 편집하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>추가적으로 <b>소설 회차 대표 이미지</b>, <b>소설 회차 제목</b>, <b>소설 회차 소개</b>, <b>소설 데이터 파일</b>, <b>소설 미디어 파일 리스트</b>를 전달한다. <br>소설 전체 정보를 불러와서 덮어씌우는 형태로 진행된다. <br>성공 시 소설 회차 정보를 반환한다.";

        // deactivateNovelDetailByReader
        public static final String deactivateNovelDetailByReaderValue = "READER 소설 회차 비활성화";
        public static final String deactivateNovelDetailByReaderNotes = "READER 권한의 사용자가 소설 회차를 비활성화하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>성공 시 'Deactivated'를 반환한다.";

        // uploadFileOnNovelDetailByReader
        public static final String uploadFileOnNovelDetailByReaderValue = "READER 소설 회차 미디어 파일 업로드";
        public static final String uploadFileOnNovelDetailByReaderNotes = "READER 권한의 사용자가 소설 회차에 이미지나 사운드를 업로드하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>추가적으로 <b>파일 목록</b>과 <b>파일 타입</b>을 전달한다. <br><b>파일 타입</b>의 경우 인자로 <u>image</u>, <u>sound</u>만 받을 수 있다. <br><b>image</b>: <u>.png</u>, <u>.jpg</u>, <u>.jpeg</u>, <u>.webp</u> 이용 가능. <br><b>sound</b>: <u>.mp3</u>, <u>.wav</u> 이용 가능. 성공 시 파일 리스트를 배열로 전달한다.";

        // createNovelByPublisher

        // viewListOfNewNovel
        public static final String viewListOfNewNovelValue = "신규 소설 목록 조회";
        public static final String viewListOfNewNovelNotes = "신규 소설 목록을 조회. 성공 시 신규 소설 목록이 최대 10개 반환된다.";

        // viewListOfPopularNovel
        public static final String viewListOfPopularNovelValue = "인기 소설 목록 조회";
        public static final String viewListOfPopularNovelNotes = "인기 소설 목록을 조회하기 위해 <b>시작 인덱스</b>와 <b>끝 인덱스</b>를 전달한다. <br>시작 인덱스는 0번부터 시작한다. <br>성공 시 인기 소설 목록이 반환된다.";

        // viewNovelAverageScore
        public static final String viewNovelAverageScoreValue = "소설 평점 조회";
        public static final String viewNovelAverageScoreNotes = "해당 소설의 평점을 조회. 성공 시 평점을 계산하여 조회한다.";
    }

    public static class documentAboutNovelCommentController {
        // createComment
        public static final String createCommentValue = "댓글 작성";
        public static final String createCommentNotes = "해당하는 소설 회차에 댓글을 달기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>추가적으로 <b>댓글 내용</b>을 기입한다. <br>성공 시 댓글 내용이 반환된다.";

        // modifyComment
        public static final String modifyCommentValue = "댓글 편집";
        public static final String modifyCommentNotes = "해당하는 소설 회차에 댓글을 수정하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>추가적으로 <b>댓글 내용</b>을 기입한다. <br>성공 시 댓글 내용이 반환된다.";

        // deactivateComment
        public static final String deactivateCommentValue = "댓글 비활성화";
        public static final String deactivateCommentNotes = "해당하는 소설 회차에 댓글을 비활성화하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>성공 시 'Deactivated'를 반환한다.";

        // viewListOfComment
        public static final String viewListOfCommentValue = "댓글 목록 조회";
        public static final String viewListOfCommentNotes = "해당하는 소설 회차의 댓글을 조회하기 위해 <b>시작 인덱스</b>, <b>끝 인덱스</b>, <b>메소드</b>를 전달. <br><b>메소드</b>의 경우 <u>new</u>와 <u>popular</u>를 입력으로 받을 수 있다. <br>성공 시 댓글 목록이 해당 메소드에 따라 내림차순으로 정렬되어 반환된다.";

        // recommendComment
        public static final String recommendCommentValue = "댓글 추천";
        public static final String recommendCommentNotes = "해당하는 댓글을 추천 또는 취소하기 위해 <b>READER</b>의 <b>JWT Access Token</b>을 <u>Authorization header</u>로 전달. <br>추천된 상태면 취소를, 추천하지 않은 상태면 추천을 하도록 하며, 비동기적으로 구현되어 있다. <br>성공 시 별도의 반환값은 없다.";
    }
}
