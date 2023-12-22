package com.sparta.plusproject.user;

import com.sparta.plusproject.global.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public void validateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }
    }

    public void signup(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
        if (userRequestDto.getPassword().contains(userRequestDto.getUsername())) {
            throw new IllegalArgumentException("비밀번호에 닉네임을 포함할 수 없습니다.");
        }
        if (!userRequestDto.getPassword().equals(userRequestDto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto,encodedPassword);
        userRepository.save(user);
    }

    public void login(UserRequestDto userRequestDto, HttpServletResponse httpServletResponse) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요"));
        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요");
        }
//        httpServletResponse.addHeader(jwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDto.getUsername()));
        String token = jwtUtil.createToken(userRequestDto.getUsername());
        jwtUtil.addJwtToCookie(token,httpServletResponse);
    }


}
