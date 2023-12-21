package com.sparta.plusproject.post;

import com.sparta.plusproject.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public void writePost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = new Post(postRequestDto,userDetails);
        postRepository.save(post);
    }
}
