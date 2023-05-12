package com.capstone.interactive_novel.reader.dto;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.common.type.Role;
import lombok.Builder;
import lombok.Data;

public class ReaderDto {
    @Data
    public static class SignUp {
        private String email;
        private String password;
        private String userName;

        public ReaderEntity toEntity() {
            return ReaderEntity.builder()
                    .email(email)
                    .userName(userName)
                    .role(Role.UNCERTIFIED)
                    .interlock("self")
                    .build();
        }
    }

    @Data
    public static class SignIn {
        private String email;
        private String password;
    }

    @Data
    @Builder
    public static class profileImg {
        private long id;
        private String email;
        private String username;
        private String imageUrl;

        public static ReaderDto.profileImg of(Long id, String email, String username, String imageUrl) {
            return profileImg.builder()
                    .id(id)
                    .email(email)
                    .username(username)
                    .imageUrl(imageUrl)
                    .build();
        }
    }

    @Data
    @Builder
    public static class view {
        private long id;
        private String email;
        private String username;
        private String imageUrl;
        private String interlock;
        private String isPaid;
        private boolean isAuthor;

        public static ReaderDto.view of(Long id, String email, String username, String imageUrl, String interlock, String isPaid, boolean isAuthor) {
            return view.builder()
                    .id(id)
                    .email(email)
                    .username(username)
                    .imageUrl(imageUrl)
                    .interlock(interlock)
                    .isPaid(isPaid)
                    .isAuthor(isAuthor)
                    .build();
        }
    }
}
