package com.sparta.plusproject.comment;

import com.sparta.plusproject.global.CommonResponseDto;
import com.sparta.plusproject.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommonResponseDto> writeComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.writeComment(id, commentRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("댓글 작성완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> modifyComment(
            @PathVariable Long id,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.modifyComment(id, commentId, commentRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("댓글 수정완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(
            @PathVariable Long id,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(id, commentId, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("댓글 삭제완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
