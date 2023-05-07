package com.capstone.interactive_novel.common.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class INovelException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public INovelException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
