package com.sparta.plusproject.comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String contents;
    private boolean likes;

    public CommentResponseDto(Comment comment) {
        this.contents = comment.getContents();
        this.likes = comment.isLikes();
    }
}
