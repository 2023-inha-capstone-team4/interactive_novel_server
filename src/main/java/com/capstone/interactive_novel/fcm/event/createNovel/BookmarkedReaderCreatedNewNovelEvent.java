package com.capstone.interactive_novel.fcm.event.createNovel;

import com.capstone.interactive_novel.novel.domain.NovelEntity;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;

public record BookmarkedReaderCreatedNewNovelEvent(ReaderEntity target,
                                                   NovelEntity novel) {
}
