package com.capstone.interactive_novel.novel.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.common.service.S3Service;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.domain.NovelStatus;
import com.capstone.interactive_novel.novel.dto.NovelDto;
import com.capstone.interactive_novel.novel.repository.NovelRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class NovelService {
    private final NovelRepository novelRepository;
    private final S3Service s3Service;
    private static final String NOVEL_DOMAIN = "novel";

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
        NovelEntity novel = NovelEntity.createNovelByReader(reader, novelName, novelIntroduce, imageUrl);
        novelRepository.save(novel);

        return NovelDto.builder()
                .id(novel.getId())
                .novelName(novelName)
                .novelIntroduce(novelIntroduce)
                .publisherName(reader.getUsername())
                .publisherType(novel.getNovelPublisherType())
                .NovelImageUrl(imageUrl)
                .totalScore(0L)
                .build();
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
        return NovelDto.builder()
                .id(novel.getId())
                .novelName(novel.getNovelName())
                .novelIntroduce(novel.getNovelIntroduce())
                .publisherName(reader.getUsername())
                .publisherType(novel.getNovelPublisherType())
                .NovelImageUrl(novel.getNovelImageUrl())
                .totalScore(novel.getTotalScore())
                .build();
    }

    public String deactivateNovelByReader(ReaderEntity reader, Long novelId) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));
        if(novel.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }
        novel.setNovelStatus(NovelStatus.DEACTIVATED);
        novelRepository.save(novel);

        return novel.getId() + ": " + NovelStatus.DEACTIVATED;
    }
}
