package com.capstone.interactive_novel.publisher.dto;

import com.capstone.interactive_novel.common.type.Role;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import lombok.Builder;
import lombok.Data;

public class PublisherDto {

    @Data
    public static class SignUp {
        private String email;
        private String password;
        private String userName;

        public PublisherEntity toEntity() {
            return PublisherEntity.builder()
                    .email(email)
                    .userName(userName)
                    .role(Role.PUBLISHER)
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
        private String url;

        public static PublisherDto.profileImg of(Long id, String email, String username, String url) {
            return PublisherDto.profileImg.builder()
                    .id(id)
                    .email(email)
                    .username(username)
                    .url(url)
                    .build();
        }
    }
}
