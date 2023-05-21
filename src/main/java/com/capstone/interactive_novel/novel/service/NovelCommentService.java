package com.capstone.interactive_novel.novel.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.novel.domain.NovelCommentEntity;
import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import com.capstone.interactive_novel.novel.dto.NovelCommentDto;
import com.capstone.interactive_novel.novel.repository.NovelCommentRepository;
import com.capstone.interactive_novel.novel.repository.NovelCommentRepositoryQuerydsl;
import com.capstone.interactive_novel.novel.repository.NovelDetailRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;
import static com.capstone.interactive_novel.novel.domain.NovelCommentStatus.DEACTIVATED;

@Service
@RequiredArgsConstructor
public class NovelCommentService {
    private final NovelDetailRepository novelDetailRepository;
    private final NovelCommentRepository novelCommentRepository;
    private final NovelCommentRepositoryQuerydsl novelCommentRepositoryQuerydsl;
    public NovelCommentDto createNovelComment(ReaderEntity reader, Long novelDetailId, String comment) {
        NovelDetailEntity novelDetail = novelDetailRepository.findById(novelDetailId)
                .orElseThrow(() -> new INovelException(NOVEL_DETAIL_NOT_FOUND));

        NovelCommentEntity novelComment = NovelCommentEntity.setComment(reader, novelDetail, 0L, comment);
        novelCommentRepository.save(novelComment);

        return NovelCommentDto.of(novelComment.getId(), reader.getId(), reader.getUsername(), comment);
    }

    public NovelCommentDto modifyNovelComment(ReaderEntity reader, Long novelDetailId, Long novelCommentId, String comment) {
        NovelDetailEntity novelDetail = novelDetailRepository.findById(novelDetailId)
                .orElseThrow(() -> new INovelException(NOVEL_DETAIL_NOT_FOUND));
        NovelCommentEntity novelComment = novelCommentRepository.findById(novelCommentId)
                .orElseThrow(() -> new INovelException(NOVEL_COMMENT_NOT_FOUND));
        if(novelComment.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }
        if(novelComment.getNovelDetail().getId() != novelDetail.getId()) {
            throw new INovelException(UNMATCHED_COMMENT_INFO);
        }

        novelComment.setComment(comment);
        novelCommentRepository.save(novelComment);

        return NovelCommentDto.of(novelComment.getId(), reader.getId(), reader.getUsername(), comment);
    }

    public void deactivateNovelComment(ReaderEntity reader, Long novelDetailId, Long novelCommentId) {
        NovelDetailEntity novelDetail = novelDetailRepository.findById(novelDetailId)
                .orElseThrow(() -> new INovelException(NOVEL_DETAIL_NOT_FOUND));
        NovelCommentEntity novelComment = novelCommentRepository.findById(novelCommentId)
                .orElseThrow(() -> new INovelException(NOVEL_COMMENT_NOT_FOUND));
        if(novelComment.getReader().getId() != reader.getId()) {
            throw new INovelException(UNMATCHED_USER_INFO);
        }
        if(novelComment.getNovelDetail().getId() != novelDetail.getId()) {
            throw new INovelException(UNMATCHED_COMMENT_INFO);
        }

        novelComment.setNovelCommentStatus(DEACTIVATED);
        novelCommentRepository.save(novelComment);
    }

    public List<NovelCommentDto> viewListOfComment(long startIdx, long endIdx, Long novelDetailId, String method) {
        NovelDetailEntity novelDetail = novelDetailRepository.findById(novelDetailId)
                .orElseThrow(() -> new INovelException(NOVEL_DETAIL_NOT_FOUND));
        switch (method) {
            case "new" -> {
                return novelCommentRepositoryQuerydsl.viewListOfNewCommentByDesc(startIdx, endIdx, novelDetail);
            }
            case "popular" -> {
                return novelCommentRepositoryQuerydsl.viewListOfPopularCommentByDesc(startIdx, endIdx, novelDetail);
            }
        }
        throw new INovelException(INVALID_PARAMETER_VALUE);
    }
}
