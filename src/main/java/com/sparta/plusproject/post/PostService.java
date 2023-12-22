package com.sparta.plusproject.post;

import com.sparta.plusproject.comment.Comment;
import com.sparta.plusproject.comment.CommentRepository;
import com.sparta.plusproject.comment.CommentResponseDto;
import com.sparta.plusproject.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void writePost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = new Post(postRequestDto, userDetails);
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

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findAllByPostId(id);
        for (Comment comment : commentList) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        return new PostResponseDto(post, commentResponseDtoList);
    }

    @Transactional
    public void modifyPost(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        if (!Objects.equals(post.getUser().getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("게시글 작성자만 수정 가능합니다.");
        }
        post.update(postRequestDto);
    }

    public void deletePost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        if (!Objects.equals(post.getUser().getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("게시글 작성자만 삭제 가능합니다.");
        }
        postRepository.delete(post);
    }
}
