package com.capstone.interactive_novel.novel.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name = "SOUND")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NovelDetailSoundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private NovelDetailEntity novelDetail;

    private String soundName;

    private String soundUrl;
}
