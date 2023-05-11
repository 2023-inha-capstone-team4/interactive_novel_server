package com.capstone.interactive_novel.novel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NovelDetailMediaDto {
    @JsonProperty("imageList")
    private List<String> imagelist;
    @JsonProperty("soundList")
    private List<String> soundList;
}
