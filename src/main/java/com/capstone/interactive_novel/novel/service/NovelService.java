package com.capstone.interactive_novel.novel.service;

import com.capstone.interactive_novel.common.service.S3Service;
import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.novel.dto.NovelDto;
import com.capstone.interactive_novel.novel.repository.NovelRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class NovelService {
    private final NovelRepository novelRepository;
    private final S3Service s3Service;

    public NovelDto createNovelByReader(ReaderEntity reader,
                                        MultipartFile file,
                                        String novelName,
                                        String novelIntroduce) {
        if(novelRepository.findByNovelName(novelName).isPresent()) {
            throw new RuntimeException("이미 존재하는 소설명입니다.");
        }
        if(!reader.isAuthorServiceYn()) {
            throw new RuntimeException("소설 게시 권한이 없는 사용자입니다.");
        }

        String imageUrl = s3Service.uploadImage(file, novelName);
        NovelEntity novel = NovelEntity.createNovelByReader(reader, novelName, novelIntroduce, imageUrl);
        novelRepository.save(novel);

        return NovelDto.builder()
                .id(novel.getId())
                .novelName(novelName)
                .novelIntroduce(novelIntroduce)
                .publisherName(reader.getUsername())
                .imageUrl(imageUrl)
                .totalScore(0L)
                .build();
    }
}
