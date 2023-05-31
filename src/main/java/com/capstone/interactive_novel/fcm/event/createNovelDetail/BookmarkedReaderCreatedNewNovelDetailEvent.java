package com.capstone.interactive_novel.fcm.event.createNovelDetail;

import com.capstone.interactive_novel.novel.domain.NovelDetailEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;

public record BookmarkedReaderCreatedNewNovelDetailEvent(ReaderEntity target,
                                                         NovelDetailEntity novelDetail) {
}
