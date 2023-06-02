package com.capstone.interactive_novel.notification.domain;

import com.capstone.interactive_novel.common.type.PublisherType;
import com.capstone.interactive_novel.fcm.domain.FcmTokenEntity;
import com.capstone.interactive_novel.notification.type.NotificationStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private FcmTokenEntity fcmToken;

    @Column(unique = true)
    private String notificationId;
    private Long authorId;
    private String authorName;
    @Enumerated(EnumType.STRING)
    private PublisherType publisherType;
    private Long novelId;
    private String novelName;
    private Long novelDetailId;
    private String novelDetailName;
    private LocalDateTime notifiedAt;
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;
}
