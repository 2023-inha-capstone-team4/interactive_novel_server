package com.capstone.interactive_novel.novel.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.kafka.components.ProducerComponents;
import com.capstone.interactive_novel.kafka.message.CommentRecommendMessage;
import com.capstone.interactive_novel.novel.domain.NovelCommentEntity;
import com.capstone.interactive_novel.novel.domain.NovelCommentRecommendEntity;
import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import com.capstone.interactive_novel.novel.dto.NovelCommentDto;
import com.capstone.interactive_novel.novel.repository.NovelCommentRecommendRepository;
import com.capstone.interactive_novel.novel.repository.NovelCommentRepository;
import com.capstone.interactive_novel.novel.repository.NovelCommentRepositoryQuerydsl;
import com.capstone.interactive_novel.novel.repository.NovelDetailRepository;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;
import static com.capstone.interactive_novel.kafka.type.KafkaTopicType.COMMENT_RECOMMEND;
import static com.capstone.interactive_novel.novel.type.NovelCommentStatus.DEACTIVATED;

@Service
@RequiredArgsConstructor
public class NovelCommentService {
    private final ReaderRepository readerRepository;
    private final NovelDetailRepository novelDetailRepository;
    private final NovelCommentRepository novelCommentRepository;
    private final NovelCommentRepositoryQuerydsl novelCommentRepositoryQuerydsl;
    private final NovelCommentRecommendRepository novelCommentRecommendRepository;
    private final ProducerComponents producerComponents;

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

    @Transactional(readOnly = true)
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

    public void sendRecommendCommentMessage(ReaderEntity reader, Long commentId) {
        CommentRecommendMessage message = new CommentRecommendMessage(reader.getId(), commentId);
        producerComponents.sendCommentRecommendMessage(COMMENT_RECOMMEND, message);
    }

    @Transactional
    public void processRecommendCommentMessage(Long readerId, Long commentId) {
        ReaderEntity reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new INovelException(USER_NOT_FOUND));

        NovelCommentEntity novelComment = novelCommentRepository.findById(commentId)
                .orElseThrow(() -> new INovelException(NOVEL_COMMENT_NOT_FOUND));
        if (novelComment.getReader().getId() == reader.getId()) {
            throw new INovelException(CANNOT_RECOMMEND_OWN_COMMENT);
        }
        if(novelComment.getNovelCommentStatus().equals(DEACTIVATED)) {
            throw new INovelException(CANNOT_RECOMMEND_CORRESPONDING_COMMENT);
        }
        Optional<NovelCommentRecommendEntity> optionalRecommend = novelCommentRecommendRepository.findByReaderAndNovelComment(reader, novelComment);

        optionalRecommend.ifPresentOrElse(r -> {
                    novelCommentRecommendRepository.delete(r);
                    novelComment.setRecommendAmount(novelComment.getRecommendAmount() - 1);
                    novelCommentRepository.save(novelComment);
                },
                () -> {
                    novelCommentRecommendRepository.save(NovelCommentRecommendEntity.builder()
                            .reader(reader)
                            .novelComment(novelComment)
                            .build());
                    novelComment.setRecommendAmount(novelComment.getRecommendAmount() + 1);
                    novelCommentRepository.save(novelComment);
                });
    }


}
