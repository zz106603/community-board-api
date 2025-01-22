package com.spring.blog.post.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Lob
    private String content;

    @Column(length = 100)
    private String writer;

    @Column(length = 100)
    private String category;

    private LocalDateTime writeDate;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;

    @Column(length = 100)
    private String deleteYn;

    private Long selectCount;
    private Long recomCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    // Getters and Setters
}