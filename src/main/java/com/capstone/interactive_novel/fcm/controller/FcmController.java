package com.capstone.interactive_novel.fcm.controller;

import com.capstone.interactive_novel.fcm.dto.FcmTokenDto;
import com.capstone.interactive_novel.fcm.service.FcmService;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {
    private final FcmService fcmService;
    @PostMapping("/login")
    public ResponseEntity<FcmTokenDto> saveFcmTokenWhenLogin(@AuthenticationPrincipal ReaderEntity reader,
                                                             @RequestBody String fcmToken) {
        return ResponseEntity.ok(fcmService.saveFcmTokenWhenLogin(reader, fcmToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<FcmTokenDto> saveFcmTokenWhenLogout(@AuthenticationPrincipal ReaderEntity reader) {
        return ResponseEntity.ok(fcmService.saveFcmTokenWhenLogout(reader));
    }
}
