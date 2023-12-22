package com.sparta.plusproject.comment;

import com.sparta.plusproject.global.security.UserDetailsImpl;
import com.sparta.plusproject.post.Post;
import com.sparta.plusproject.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public void writeComment(Long id,CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));
        Comment comment = new Comment(post,commentRequestDto,userDetails);
        commentRepository.save(comment);
    }
}
