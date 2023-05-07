package com.capstone.interactive_novel.common.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.capstone.interactive_novel.common.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException: " + e.getMessage());
        return ResponseEntity.internalServerError().body(
            ErrorResponseDto.builder()
                    .errorCode(WRONG_METHOD_OR_URL)
                    .errorMessage(WRONG_METHOD_OR_URL.getDescription())
                    .build());
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAmazonS3Exception(AmazonS3Exception e) {
        log.error("AmazonS3Exception: " + e.getMessage());
        return ResponseEntity.internalServerError().body(
                ErrorResponseDto.builder()
                        .errorCode(AMAZON_S3_ERROR)
                        .errorMessage(e.getErrorMessage())
                        .build());
    }

    @ExceptionHandler(INovelException.class)
    public ResponseEntity<ErrorResponseDto> handleINovelException(INovelException e) {
        log.error("INovelException: " + e.getErrorMessage());
        return ResponseEntity.internalServerError().body(
                ErrorResponseDto.builder()
                        .errorCode(e.getErrorCode())
                        .errorMessage(e.getErrorMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error("Exception: " + e.getMessage());
        return ResponseEntity.internalServerError().body(
                ErrorResponseDto.builder()
                        .errorCode(INTERNAL_ERROR)
                        .errorMessage(INTERNAL_ERROR.getDescription())
                        .build());
    }
}
