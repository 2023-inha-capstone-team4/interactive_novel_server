package com.capstone.interactive_novel.fcm.listener;

import com.capstone.interactive_novel.bookmark.domain.BookmarkReaderEntity;
import com.capstone.interactive_novel.bookmark.repository.BookmarkReaderRepository;
import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.fcm.domain.FcmTokenEntity;
import com.capstone.interactive_novel.fcm.dto.FcmMessageInfoDto;
import com.capstone.interactive_novel.fcm.event.createNovel.BookmarkedReaderCreatedNewNovelEvent;
import com.capstone.interactive_novel.fcm.repository.FcmTokenRepository;
import com.capstone.interactive_novel.fcm.service.FcmService;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.capstone.interactive_novel.common.exception.ErrorCode.BOOKMARKED_USER_NOT_FOUND;
import static com.capstone.interactive_novel.common.type.PublisherType.READER;
import static com.capstone.interactive_novel.notification.type.NotificationType.BOOKMARKED_USER_CREATED_NEW_NOVEL;

@Slf4j
@Component
@Async("fcmAsyncConfiguration")
@RequiredArgsConstructor
public class CreateNovelEventListener {
    private final FcmTokenRepository fcmTokenRepository;
    private final BookmarkReaderRepository bookmarkReaderRepository;
    private final FcmService fcmService;

    @EventListener
    public void handleBookmarkedReaderCreatedNovelEvent(BookmarkedReaderCreatedNewNovelEvent event) {
        try {
            ReaderEntity target = event.target();
            NovelEntity novel = event.novel();
            List<ReaderEntity> bookmarkedMemberList = bookmarkReaderRepository.findAllByTarget(target)
                    .orElseThrow(() -> new INovelException(BOOKMARKED_USER_NOT_FOUND))
                    .stream()
                    .map(BookmarkReaderEntity::getReader)
                    .toList();

            List<String> tokenList = new ArrayList<>();

            for(ReaderEntity bookmarkedReader : bookmarkedMemberList) {
                Optional<FcmTokenEntity> optionalFcmToken = fcmTokenRepository.findByReader(bookmarkedReader);
                if(optionalFcmToken.isEmpty()) {
                    log.info("해당 회원의 알림 정보가 없습니다: " + bookmarkedReader.getUsername());
                    continue;
                }

                FcmTokenEntity fcmToken = optionalFcmToken.get();
                if(fcmToken.isLoginStatus()) {
                    tokenList.add(optionalFcmToken.get().getFcmToken());
                }
            }

            fcmService.sendMessages(tokenList, FcmMessageInfoDto.of(target.getId(), target.getUsername(), READER, novel.getId(), novel.getNovelName(), null, null, BOOKMARKED_USER_CREATED_NEW_NOVEL, LocalDateTime.now()));

        } catch (Exception e) {
            log.error("FCM 메시지 전송 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }
    }
}
