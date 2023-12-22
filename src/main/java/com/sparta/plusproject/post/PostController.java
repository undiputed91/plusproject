package com.sparta.plusproject.post;

import com.sparta.plusproject.global.CommonResponseDto;
import com.sparta.plusproject.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<CommonResponseDto> writePost(
            @Valid @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            postService.writePost(postRequestDto,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("게시글 작성완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping
    public List<PostListResponseDto> getPostList(){
         return  postService.getPostList();
    }

    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponseDto> modifyPost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            postService.modifyPost(id,postRequestDto,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("게시글 수정완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
}
