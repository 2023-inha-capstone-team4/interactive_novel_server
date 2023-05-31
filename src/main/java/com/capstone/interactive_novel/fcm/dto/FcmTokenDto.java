package com.capstone.interactive_novel.fcm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FcmTokenDto {
    private long id;
    private long readerId;
    private String readerName;
    private String fcmToken;
    private boolean loginStatus;

    public static FcmTokenDto of(long id, long readerId, String readerName, String fcmToken, boolean loginStatus) {
        return FcmTokenDto.builder()
                .id(id)
                .readerId(readerId)
                .readerName(readerName)
                .fcmToken(fcmToken)
                .loginStatus(loginStatus)
                .build();
    }
}
