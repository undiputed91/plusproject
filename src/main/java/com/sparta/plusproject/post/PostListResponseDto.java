package com.sparta.plusproject.post;

import com.sparta.plusproject.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private String title;
    private String username;
    private LocalDateTime createdAt;

    public PostListResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
    }
}
