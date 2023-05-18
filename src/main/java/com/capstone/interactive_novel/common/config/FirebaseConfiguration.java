package com.capstone.interactive_novel.common.config;

import com.capstone.interactive_novel.common.exception.ErrorCode;
import com.capstone.interactive_novel.common.exception.INovelException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfiguration {
    @Value("${fcm.key.path}")
    private String fcmPrivateKey;

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials
                            .fromStream(new ClassPathResource(fcmPrivateKey).getInputStream()))
                    .build();
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase가 정상적으로 초기화되었습니다.");
            }
        } catch (IOException e) {
            throw new INovelException(ErrorCode.CANNOT_INIT_FIREBASE);
        }
    }
}
