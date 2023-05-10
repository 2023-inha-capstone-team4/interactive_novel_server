package com.capstone.interactive_novel.novel.dto;

import com.capstone.interactive_novel.novel.domain.NovelDetailImageEntity;
import com.capstone.interactive_novel.novel.domain.NovelDetailSoundEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NovelDetailMediaDto {
    private List<NovelDetailImageEntity> imagelist;
    private List<NovelDetailSoundEntity> soundList;
}
