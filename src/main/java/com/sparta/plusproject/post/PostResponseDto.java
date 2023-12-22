package com.sparta.plusproject.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
}
