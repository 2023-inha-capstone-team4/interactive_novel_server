package com.capstone.interactive_novel.common.dto;

import com.capstone.interactive_novel.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private ErrorCode errorCode;
    private String errorMessage;
}
