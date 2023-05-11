package com.capstone.interactive_novel.bookmark.domain;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkReaderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ReaderEntity reader;
    @ManyToOne
    private ReaderEntity target;
}
