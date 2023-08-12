package com.github.practice.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // 자동 증가 전략이 데이터베이스를 따라간다
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_user_id")
    private UserEntity writer;

    private String title;
    private String body;
    private LocalDateTime createdAt;


}
