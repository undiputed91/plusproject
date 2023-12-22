package com.sparta.plusproject.post;

import com.sparta.plusproject.comment.Comment;
import com.sparta.plusproject.comment.CommentResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private Page<Comment> commentList;

    public PostResponseDto(Post post, Page<Comment> commentResponseDtoList) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.commentList = commentResponseDtoList;
    }
}
