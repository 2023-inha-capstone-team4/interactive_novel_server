package com.capstone.interactive_novel.novel.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name = "IMAGE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NovelDetailImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private NovelDetailEntity novelDetail;

    private String imageName;

    private String imageUrl;
}
