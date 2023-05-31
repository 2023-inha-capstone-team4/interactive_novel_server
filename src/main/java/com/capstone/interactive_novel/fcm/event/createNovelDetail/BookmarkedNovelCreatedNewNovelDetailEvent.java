package com.capstone.interactive_novel.fcm.event.createNovelDetail;

import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import com.capstone.interactive_novel.novel.domain.NovelEntity;

public record BookmarkedNovelCreatedNewNovelDetailEvent(NovelEntity target,
                                                        NovelDetailEntity novelDetail) {
}
