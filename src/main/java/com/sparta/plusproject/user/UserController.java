package com.sparta.plusproject.user;

import com.sparta.plusproject.global.CommonResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup/validate")
    public ResponseEntity<CommonResponseDto> validateUsername(@RequestParam String username) {
        try {
            userService.validateUsername(username);
            return ResponseEntity.ok().body(new CommonResponseDto("사용할 수 있는 닉네임입니다.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            userService.signup(userRequestDto);
            return ResponseEntity.ok().body(new CommonResponseDto("회원가입 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse httpServletResponse) {
        try {
            userService.login(userRequestDto, httpServletResponse);
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
