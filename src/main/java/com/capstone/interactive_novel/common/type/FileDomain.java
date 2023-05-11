package com.capstone.interactive_novel.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileDomain {
    NOVEL_DOMAIN("novel"),
    NOVEL_IMAGE_DOMAIN("novel_image"),
    NOVEL_SOUND_DOMAIN("novel_sound");

    private final String description;
}
