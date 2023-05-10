package com.capstone.interactive_novel.novel.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.common.service.S3Service;
import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.dto.NovelDetailDto;
import com.capstone.interactive_novel.novel.dto.NovelDetailMediaDto;
import com.capstone.interactive_novel.novel.repository.NovelDetailRepository;
import com.capstone.interactive_novel.novel.repository.NovelRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.capstone.interactive_novel.common.exception.ErrorCode.NOVEL_NOT_FOUND;
import static com.capstone.interactive_novel.common.exception.ErrorCode.UNMATCHED_USER_INFO;

@Service
@RequiredArgsConstructor
public class NovelDetailService {
    private final NovelRepository novelRepository;
    private final NovelDetailRepository novelDetailRepository;
    private final S3Service s3Service;

    public NovelDetailDto createNovelDetailByReader(ReaderEntity reader,
                                                    Long novelId,
                                                    MultipartFile file,
                                                    String novelDetailName,
                                                    String novelDetailIntroduce,
                                                    MultipartFile novelScriptFile,
                                                    NovelDetailMediaDto mediaDto) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));

        if(novel.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }

        String imageUrl = s3Service.uploadImage(file, "novel", novel.getNovelName());
        NovelDetailEntity novelDetail = NovelDetailEntity.createNovelDetail(novelDetailName, novelDetailIntroduce, imageUrl, novel, novelScriptFile, mediaDto);
        novelDetailRepository.save(novelDetail);

        return NovelDetailDto.builder()
                .id(novelDetail.getId())
                .novelId(novel.getId())
                .novelDetailName(novelDetailName)
                .novelDetailIntroduce(novelDetailIntroduce)
                .novelPublisherType(novelDetail.getNovelPublisherType())
                .publisherName(reader.getUsername())
                .novelImageUrl(imageUrl)
                .totalScore(0L)
                .mediaDto(mediaDto)
                .build();
    }
}
