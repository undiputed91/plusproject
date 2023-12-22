package com.sparta.plusproject.post;

import com.sparta.plusproject.comment.Comment;
import com.sparta.plusproject.comment.CommentRepository;
import com.sparta.plusproject.comment.CommentResponseDto;
import com.sparta.plusproject.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<PostListResponseDto> getPostList(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Post> postList;
        postList = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return postList.map(PostListResponseDto::new);
    }

    public PostResponseDto getPost(Long id, int page, int size, String sortBy, boolean isAsc) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Comment> commentList;
        commentList = commentRepository.findAllByPostId(id,pageable);

        return new PostResponseDto(post, commentList);
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
