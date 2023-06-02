package com.capstone.interactive_novel.novel.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.common.service.S3Service;
import com.capstone.interactive_novel.fcm.event.createNovel.BookmarkedReaderCreatedNewNovelEvent;
import com.capstone.interactive_novel.novel.type.NovelDetailStatus;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.type.NovelStatus;
import com.capstone.interactive_novel.novel.dto.NovelDto;
import com.capstone.interactive_novel.novel.repository.NovelDetailRepositoryQuerydsl;
import com.capstone.interactive_novel.novel.repository.NovelRepository;
import com.capstone.interactive_novel.novel.repository.NovelRepositoryQuerydsl;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.publisher.repository.PublisherRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;
import static com.capstone.interactive_novel.common.type.PublisherType.PUBLISHER;
import static com.capstone.interactive_novel.common.type.PublisherType.READER;
import static com.capstone.interactive_novel.novel.type.NovelStatus.FREE;
import static com.capstone.interactive_novel.novel.type.NovelStatus.PAY;

@RequiredArgsConstructor
@Service
public class NovelService {
    private final ReaderRepository readerRepository;
    private final PublisherRepository publisherRepository;
    private final NovelRepository novelRepository;
    private final NovelRepositoryQuerydsl novelRepositoryQuerydsl;
    private final NovelDetailRepositoryQuerydsl novelDetailRepositoryQuerydsl;
    private final ApplicationEventPublisher eventPublisher;
    private final S3Service s3Service;
    private static final String NOVEL_DOMAIN = "novel";

    // Reader 관련

    public NovelDto createNovelByReader(ReaderEntity reader,
                                        MultipartFile file,
                                        String novelName,
                                        String novelIntroduce) {
        if(novelRepository.findByNovelName(novelName).isPresent()) {
            throw new INovelException(ALREADY_USING_NOVEL_NAME);
        }
        if(!reader.isAuthorServiceYn()) {
            throw new INovelException(UNVERIFIED_USER);
        }

        String imageUrl = s3Service.uploadFile(file, NOVEL_DOMAIN, novelName);
        NovelEntity novel = NovelEntity.createNovelByReader(reader, novelName, reader.getUsername(), reader.getId(), novelIntroduce, imageUrl);
        novelRepository.save(novel);

        eventPublisher.publishEvent(new BookmarkedReaderCreatedNewNovelEvent(reader, novel));

        return NovelDto.of(novel.getId(), novel.getNovelName(), reader.getUsername(), reader.getId(), novel.getNovelIntroduce(), READER, novel.getNovelImageUrl(), novel.getTotalScore());
    }

    public NovelDto modifyNovelByReader(ReaderEntity reader,
                                        Long novelId,
                                        MultipartFile file,
                                        String novelIntroduce) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));
        if(novel.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }
        if(!ObjectUtils.isEmpty(file)) {
            novel.setNovelImageUrl(s3Service.uploadFile(file, NOVEL_DOMAIN, novel.getNovelName()));
        }
        if(!ObjectUtils.isEmpty(novelIntroduce)) {
            novel.setNovelIntroduce(novelIntroduce);
        }

        novelRepository.save(novel);
        return NovelDto.of(novel.getId(), novel.getNovelName(), reader.getUsername(), reader.getId(), novel.getNovelIntroduce(), READER, novel.getNovelImageUrl(), novel.getTotalScore());
    }

    @Transactional
    public void deactivateNovelByReader(ReaderEntity reader, Long novelId) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));
        if(novel.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }

        novelDetailRepositoryQuerydsl.updateAllNovelDetailStatus(novelId, NovelDetailStatus.DEACTIVATED);
        novel.setNovelStatus(NovelStatus.DEACTIVATED);
        novelRepository.save(novel);
    }

    public List<NovelDto> viewOwnNovelListByReader(ReaderEntity reader, long startIdx, long endIdx) {
        return novelRepositoryQuerydsl.viewListOfNovelAboutReader(reader, startIdx, endIdx);
    }

    // Publisher 관련

    public NovelDto createNovelByPublisher(PublisherEntity publisher,
                                           MultipartFile file,
                                           String novelName,
                                           String novelIntroduce,
                                           String payType) {
        if(novelRepository.findByNovelName(novelName).isPresent()) {
            throw new INovelException(ALREADY_USING_NOVEL_NAME);
        }

        NovelStatus novelStatus;
        if(payType.equals(FREE.toString())) {
            novelStatus = FREE;
        }
        else if(payType.equals(PAY.toString())) {
            novelStatus = PAY;
        }
        else {
            throw new INovelException(INVALID_PAY_TYPE);
        }

        String imageUrl = s3Service.uploadFile(file, NOVEL_DOMAIN, novelName);
        NovelEntity novel = NovelEntity.createNovelByPublisher(publisher, novelName, publisher.getUsername(), publisher.getId(),novelIntroduce, imageUrl, novelStatus);
        novelRepository.save(novel);

        return NovelDto.of(novel.getId(), novel.getNovelName(), publisher.getUsername(), publisher.getId(), novel.getNovelIntroduce(), PUBLISHER, novel.getNovelImageUrl(), novel.getTotalScore());
    }

    // 전체 사용
    public List<NovelDto> viewListOfNewNovel() {
        return novelRepositoryQuerydsl.viewListOfNewNovel();
    }

    public List<NovelDto> viewListOfPopularNovel(long startIdx, long endIdx) {
        return novelRepositoryQuerydsl.viewListOfPopularNovel(startIdx, endIdx);
    }

    public List<NovelDto> viewListOfAuthorNovel(Long authorId, String type, long startIdx, long endIdx) {
        switch (type) {
            case "reader" -> {
                ReaderEntity reader = readerRepository.findById(authorId)
                        .orElseThrow(() -> new INovelException(USER_NOT_FOUND));
                return novelRepositoryQuerydsl.viewListOfNovelAboutReader(reader, startIdx, endIdx);
            }
            case "publisher" -> {
                PublisherEntity publisher = publisherRepository.findById(authorId)
                        .orElseThrow(() -> new INovelException(USER_NOT_FOUND));
                return novelRepositoryQuerydsl.viewListOfNovelAboutPublisher(publisher, startIdx, endIdx);
            }
            default -> {
            }
        }
        throw new INovelException(INVALID_PARAMETER_VALUE);
    }

    public List<NovelDto> viewListOfKeywordSearch(String keyword, long startIdx, long endIdx) {
        return novelRepositoryQuerydsl.viewListOfKeywordSearchNovel(keyword, startIdx, endIdx);
    }

    public Double viewNovelAverageScore(Long novelId) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));
        return novel.getReviewerAmount() == 0 ?
                0.0 : ((double) novel.getTotalScore()) / ((double) novel.getReviewerAmount());
    }

}
