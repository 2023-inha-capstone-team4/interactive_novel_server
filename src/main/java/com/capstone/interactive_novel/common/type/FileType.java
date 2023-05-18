package com.capstone.interactive_novel.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum FileType {
    IMAGE("image", List.of(".png", ".jpg", ".jpeg", ".webp")),
    SOUND("sound", List.of(".mp3", ".wav"));

    private final String fileType;
    private final List<String> allowedFileType;
}
