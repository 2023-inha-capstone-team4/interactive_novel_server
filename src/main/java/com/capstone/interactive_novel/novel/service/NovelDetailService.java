package com.capstone.interactive_novel.novel.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.common.service.S3Service;
import com.capstone.interactive_novel.common.type.FileDomain;
import com.capstone.interactive_novel.common.utils.FileUtils;
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

import java.util.ArrayList;
import java.util.List;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;
import static com.capstone.interactive_novel.common.type.FileType.IMAGE;
import static com.capstone.interactive_novel.common.type.FileType.SOUND;
import static com.capstone.interactive_novel.novel.domain.NovelDetailStatus.DEACTIVATED;

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
                                                    MultipartFile novelDataFile,
                                                    NovelDetailMediaDto mediaDto) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));

        if(novel.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }

        String imageUrl = s3Service.uploadFile(file, "novel", novel.getNovelName());
        NovelDetailEntity novelDetail = NovelDetailEntity.setNovelDetail(novelDetailName, novelDetailIntroduce, imageUrl, novel, novelDataFile, mediaDto);
        novelDetailRepository.save(novelDetail);

        return NovelDetailDto.builder()
                .id(novelDetail.getId())
                .novelId(novel.getId())
                .novelDetailName(novelDetailName)
                .novelDetailIntroduce(novelDetailIntroduce)
                .publisherType(novelDetail.getPublisherType())
                .authorName(reader.getUsername())
                .novelImageUrl(imageUrl)
                .totalScore(0L)
                .mediaDto(mediaDto)
                .build();
    }

    public NovelDetailDto modifyNovelDetailByReader(ReaderEntity reader,
                                                    Long novelId,
                                                    Long novelDetailId,
                                                    MultipartFile file,
                                                    String novelDetailName,
                                                    String novelDetailIntroduce,
                                                    MultipartFile novelDataFile,
                                                    NovelDetailMediaDto mediaDto) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));
        NovelDetailEntity novelDetail = novelDetailRepository.findById(novelDetailId)
                .orElseThrow(() -> new INovelException(NOVEL_DETAIL_NOT_FOUND));
        if(novel.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }
        if(novelDetail.getNovel().getId() != novelId) {
            throw new INovelException(UNMATCHED_NOVEL_INFO);
        }

        String imageUrl = s3Service.uploadFile(file, "novel", novel.getNovelName());
        novelDetail = NovelDetailEntity.setNovelDetail(novelDetailName, novelDetailIntroduce, imageUrl, novel, novelDataFile, mediaDto);
        novelDetailRepository.save(novelDetail);

        return NovelDetailDto.builder()
                .id(novelDetail.getId())
                .novelId(novel.getId())
                .novelDetailName(novelDetailName)
                .novelDetailIntroduce(novelDetailIntroduce)
                .publisherType(novelDetail.getPublisherType())
                .authorName(reader.getUsername())
                .novelImageUrl(imageUrl)
                .totalScore(0L)
                .mediaDto(mediaDto)
                .build();
    }

    public void deactivateNovelDetailByReader(ReaderEntity reader, Long novelId, Long novelDetailId) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));
        NovelDetailEntity novelDetail = novelDetailRepository.findById(novelDetailId)
                .orElseThrow(() -> new INovelException(NOVEL_DETAIL_NOT_FOUND));
        if(novel.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }
        if(novelDetail.getNovel().getId() != novelId) {
            throw new INovelException(UNMATCHED_NOVEL_INFO);
        }
        novelDetail.setNovelDetailStatus(DEACTIVATED);
        novelDetailRepository.save(novelDetail);
    }

    public List<String> uploadFilesByReader(ReaderEntity reader,
                                            Long novelId,
                                            Long novelDetailId,
                                            MultipartFile[] files,
                                            String fileType) {
        NovelEntity novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new INovelException(NOVEL_NOT_FOUND));
        NovelDetailEntity novelDetail = novelDetailRepository.findById(novelDetailId)
                .orElseThrow(() -> new INovelException(NOVEL_DETAIL_NOT_FOUND));
        if(novel.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }
        if(novelDetail.getNovel().getId() != novelId) {
            throw new INovelException(UNMATCHED_NOVEL_INFO);
        }

        List<String> fileList = new ArrayList<>();

        if(fileType.toLowerCase().equals(IMAGE.getFileType())) {
            for (MultipartFile file : files) {
                if(!FileUtils.checkExtension(file, IMAGE.getAllowedFileType())) {
                    throw new INovelException(WRONG_FILE_EXTENSION);
                }
                fileList.add(s3Service.uploadFile(file, FileDomain.NOVEL_IMAGE_DOMAIN.getDescription(), novel.getId() + "/" + novelDetail.getId()));
            }
        }
        else if(fileType.toLowerCase().equals(SOUND.getFileType())) {
            for (MultipartFile file : files) {
                if(!FileUtils.checkExtension(file, SOUND.getAllowedFileType())) {
                    throw new INovelException(WRONG_FILE_EXTENSION);
                }
                fileList.add(s3Service.uploadFile(file, FileDomain.NOVEL_SOUND_DOMAIN.getDescription(), novel.getId() + "/" + novelDetail.getId()));
            }
        }
        else {
            throw new INovelException(INVALID_FILE_TYPE);
        }
        return fileList;
    }
}
