package com.capstone.interactive_novel.novel.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NovelCategoryType {
    ROMANCE("romance", "로맨스"),
    FANTASY("fantasy", "판타지"),
    ACTION("action", "액션"),
    DAILY("daily", "일상"),
    THRILLER("thriller", "스릴러"),
    GAG("gag", "개그"),
    HISTORIC("historic", "사극"),
    DRAMA("drama", "드라마"),
    EMOTION("emotion", "감성"),
    SPORTS("sports", "스포츠");
    private final String key;
    private final String description;
}
