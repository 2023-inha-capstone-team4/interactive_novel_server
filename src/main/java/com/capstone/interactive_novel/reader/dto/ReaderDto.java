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
        private String email;
        private String username;
        private String domain;
        private String url;

        public static ReaderDto.profileImg of(String email, String username, String domain, String url) {
            return profileImg.builder()
                    .email(email)
                    .username(username)
                    .domain(domain)
                    .url(url)
                    .build();
        }
    }
}
