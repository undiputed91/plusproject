package com.sparta.plusproject.post;

import com.sparta.plusproject.comment.CommentResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;

    public PostResponseDto(Post post, List<CommentResponseDto> commentResponseDtoList) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.commentList = commentResponseDtoList;
    }
}
