package com.sparta.plusproject.post;

import com.sparta.plusproject.global.Timestamped;
import com.sparta.plusproject.global.security.UserDetailsImpl;
import com.sparta.plusproject.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.user= userDetails.getUser();
    }
}
