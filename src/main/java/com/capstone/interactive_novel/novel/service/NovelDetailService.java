package com.capstone.interactive_novel.novel.service;

import com.capstone.interactive_novel.common.service.S3Service;
import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.dto.NovelDetailDto;
import com.capstone.interactive_novel.novel.repository.NovelDetailRepository;
import com.capstone.interactive_novel.novel.repository.NovelRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
                                                    MultipartFile novelScriptFile) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 소설입니다."));

        if(novel.getReader().getId() != reader.getId()) {
            throw new RuntimeException("사용자 정보가 일치하지 않습니다.");
        }

        String imageUrl = s3Service.uploadImage(file, "novel", novel.getNovelName());
        NovelDetailEntity novelDetail = NovelDetailEntity.createNovelDetail(novelDetailName, novelDetailIntroduce, imageUrl, novel, novelScriptFile);
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
                .build();
    }
}
