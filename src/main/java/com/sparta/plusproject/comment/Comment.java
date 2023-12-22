package com.sparta.plusproject.comment;

import com.sparta.plusproject.global.security.UserDetailsImpl;
import com.sparta.plusproject.post.Post;
import com.sparta.plusproject.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @Column
    private boolean likes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Comment(Post post, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        this.post = post;
        this.contents = commentRequestDto.getContents();
        this.likes = commentRequestDto.isLikes();
        this.user = userDetails.getUser();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();
        this.likes = commentRequestDto.isLikes();
    }
}
