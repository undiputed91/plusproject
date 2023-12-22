package com.sparta.plusproject.comment;

import com.sparta.plusproject.global.security.UserDetailsImpl;
import com.sparta.plusproject.post.Post;
import com.sparta.plusproject.post.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    @Transactional
    public void modifyComment(Long id, Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        if(!Objects.equals(comment.getUser().getId(), userDetails.getUser().getId())){
            throw new IllegalArgumentException("댓글 작성자만 수정 가능합니다.");
        }
        comment.update(commentRequestDto);
    }

    public void deleteComment(Long id, Long commentId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        if(!Objects.equals(comment.getUser().getId(), userDetails.getUser().getId())){
            throw new IllegalArgumentException("댓글 작성자만 삭제 가능합니다.");
        }
        commentRepository.delete(comment);
    }
}
