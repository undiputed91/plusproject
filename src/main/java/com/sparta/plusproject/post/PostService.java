package com.sparta.plusproject.post;

import com.sparta.plusproject.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public void writePost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = new Post(postRequestDto,userDetails);
        postRepository.save(post);
    }

    public List<PostListResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostListResponseDto> responseList = new ArrayList<>();
        for (Post post : postList) {
            responseList.add(new PostListResponseDto(post));
        }
        return responseList;
    }
}
