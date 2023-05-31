package com.capstone.interactive_novel.fcm.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.fcm.domain.FcmTokenEntity;
import com.capstone.interactive_novel.fcm.dto.FcmMessageInfoDto;
import com.capstone.interactive_novel.fcm.dto.FcmTokenDto;
import com.capstone.interactive_novel.fcm.repository.FcmTokenRepository;
import com.capstone.interactive_novel.notification.repository.NotificationRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

import static com.capstone.interactive_novel.common.exception.ErrorCode.FAILED_TO_SEND_FIREBASE_MESSAGE;
import static com.capstone.interactive_novel.common.exception.ErrorCode.FCM_TOKEN_NOT_FOUND;
import static com.capstone.interactive_novel.fcm.type.FcmMessageParameterType.*;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FcmTokenRepository fcmTokenRepository;
    private final NotificationRepository notificationRepository;
    public FcmTokenDto saveFcmTokenWhenLogin(ReaderEntity reader, String fcmToken) {
        FcmTokenEntity fcmTokenEntity = fcmTokenRepository.findByReader(reader)
                .map(fcmEntity -> {
                    fcmEntity.setFcmToken(fcmToken);
                    return fcmTokenRepository.save(fcmEntity);
                }).orElseGet(() -> fcmTokenRepository.save(FcmTokenEntity.builder()
                        .reader(reader)
                        .fcmToken(fcmToken)
                        .loginStatus(true)
                        .build()));
        return FcmTokenDto.of(fcmTokenEntity.getId(), fcmTokenEntity.getReader().getId(), fcmTokenEntity.getReader().getUsername(), fcmToken, true);
    }

    public FcmTokenDto saveFcmTokenWhenLogout(ReaderEntity reader) {
        FcmTokenEntity fcmTokenEntity = fcmTokenRepository.findByReader(reader)
                .orElseThrow(() -> new INovelException(FCM_TOKEN_NOT_FOUND));
        fcmTokenEntity.setLoginStatus(false);
        fcmTokenRepository.save(fcmTokenEntity);
        return FcmTokenDto.of(fcmTokenEntity.getId(), fcmTokenEntity.getReader().getId(), fcmTokenEntity.getReader().getUsername(), fcmTokenEntity.getFcmToken(), false);
    }

    public void sendMessages(List<String> tokenList, FcmMessageInfoDto messageInfoDto) {
        for(String token : tokenList) {
            WebpushConfig webpushConfig = WebpushConfig.builder()
                    .setNotification(new WebpushNotification(messageInfoDto.getNotificationType().toString(), messageInfoDto.getNotificationType().getMessage()))
                    .build();

            String notificationId = UUID.randomUUID().toString().replace("-", "");

            Message.Builder builder = Message.builder();
            builder.setToken(token);
            builder.setWebpushConfig(webpushConfig);
            builder.putData(NOTIFICATION_ID, notificationId);
            builder.putData(NOTIFICATION_TYPE, messageInfoDto.getNotificationType().toString());
            builder.putData(NOTIFICATION_MESSAGE, messageInfoDto.getNotificationType().getMessage());
            builder.putData(NOTIFIED_AT, messageInfoDto.getNotifiedAt().toString());

            if(!ObjectUtils.isEmpty(messageInfoDto.getAuthorId())) {
                builder.putData(AUTHOR_ID, String.valueOf(messageInfoDto.getAuthorId()));
            }
            if(!ObjectUtils.isEmpty(messageInfoDto.getAuthorName())) {
                builder.putData(AUTHOR_NAME, messageInfoDto.getAuthorName());
            }
            if(!ObjectUtils.isEmpty(messageInfoDto.getPublisherType())) {
                builder.putData(AUTHOR_TYPE, messageInfoDto.getPublisherType().toString());
            }
            if(!ObjectUtils.isEmpty(messageInfoDto.getNovelId())) {
                builder.putData(NOVEL_ID, String.valueOf(messageInfoDto.getNovelId()));
            }
            if(!ObjectUtils.isEmpty(messageInfoDto.getNovelName())) {
                builder.putData(NOVEL_NAME, messageInfoDto.getNovelName());
            }
            if(!ObjectUtils.isEmpty(messageInfoDto.getNovelDetailId())) {
                builder.putData(NOVEL_DETAIL_ID, String.valueOf(messageInfoDto.getNovelDetailId()));
            }
            if(!ObjectUtils.isEmpty(messageInfoDto.getNovelDetailName())) {
                builder.putData(NOVEL_DETAIL_NAME, messageInfoDto.getNovelDetailName());
            }

            Message message = builder.build();

            try {
                FirebaseMessaging.getInstance().send(message);
                notificationRepository.save(FcmMessageInfoDto.toEntityWhenSendMessage(notificationId, messageInfoDto));
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
                throw new INovelException(FAILED_TO_SEND_FIREBASE_MESSAGE);
            }
        }
    }
}
