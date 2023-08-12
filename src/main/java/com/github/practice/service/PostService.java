package com.github.practice.service;

import com.github.practice.domain.Member;
import com.github.practice.domain.Post;
import com.github.practice.repository.MemberRepository;
import com.github.practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();

    }


    public Post writePost(String title, String body, Long writerMemberId) {
        System.out.println("writerMemberId: " + writerMemberId); // 로그 출력 추가


        if (writerMemberId == null) {
            throw new IllegalArgumentException("Writer member ID must not be null");
        }
        Member writer = memberRepository.findById(writerMemberId).orElseThrow(() -> new IllegalArgumentException("Writer not found with ID: " + writerMemberId));
        return postRepository.save(
                Post.builder()
                        .title(title)
                        .body(body)
                        .writer(writer)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

    }
}
