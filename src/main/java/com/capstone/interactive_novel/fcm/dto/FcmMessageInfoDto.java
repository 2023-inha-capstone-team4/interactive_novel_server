package com.capstone.interactive_novel.fcm.dto;

import com.capstone.interactive_novel.common.type.PublisherType;
import com.capstone.interactive_novel.notification.domain.NotificationEntity;
import com.capstone.interactive_novel.notification.type.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.capstone.interactive_novel.notification.type.NotificationStatus.UNREAD;

@Data
@Builder
public class FcmMessageInfoDto {
    private Long authorId;
    private String authorName;
    private PublisherType publisherType;
    private Long novelId;
    private String novelName;
    private Long novelDetailId;
    private String novelDetailName;
    private NotificationType notificationType;
    private LocalDateTime notifiedAt;

    public static FcmMessageInfoDto of(Long authorId, String authorName, PublisherType publisherType, Long novelId, String novelName, Long novelDetailId, String novelDetailName, NotificationType notificationType, LocalDateTime notifiedAt) {
        return FcmMessageInfoDto.builder()
                .authorId(authorId)
                .authorName(authorName)
                .publisherType(publisherType)
                .novelId(novelId)
                .novelName(novelName)
                .novelDetailId(novelDetailId)
                .novelDetailName(novelDetailName)
                .notificationType(notificationType)
                .notifiedAt(notifiedAt)
                .build();
    }
    public static NotificationEntity toEntityWhenSendMessage(String notificationId, FcmMessageInfoDto fcmMessageInfoDto) {
        return NotificationEntity.builder()
                .notificationId(notificationId)
                .authorId(fcmMessageInfoDto.getAuthorId())
                .authorName(fcmMessageInfoDto.getAuthorName())
                .publisherType(fcmMessageInfoDto.getPublisherType())
                .novelId(fcmMessageInfoDto.getNovelId())
                .novelName(fcmMessageInfoDto.getNovelName())
                .novelDetailId(fcmMessageInfoDto.getNovelDetailId())
                .novelDetailName(fcmMessageInfoDto.getNovelDetailName())
                .notifiedAt(fcmMessageInfoDto.getNotifiedAt())
                .notificationStatus(UNREAD)
                .build();
    }
}
