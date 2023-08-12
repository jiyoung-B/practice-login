package com.github.practice.controller;

import com.github.practice.domain.Post;
import com.github.practice.dto.AuthInfo;
import com.github.practice.dto.PostRequest;
import com.github.practice.dto.PostResponse;
import com.github.practice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vi/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @PostMapping
    public ResponseEntity<PostResponse> writePost(
            AuthInfo authInfo,
            @RequestBody PostRequest postRequest
    ){
        System.out.println("오쓰인포"+authInfo);
        System.out.println("오쓰인포ID"+authInfo.getMemberId());
        Post post = postService.writePost(postRequest.getTitle(), postRequest.getBody(), authInfo.getMemberId());
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(AuthInfo authInfo){
        return ResponseEntity.ok(
                postService.getAllPosts().stream()
                        .map(PostResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
