package com.sparta.plusproject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signup(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
        if(userRequestDto.getPassword().contains(userRequestDto.getUsername())){
            throw new IllegalArgumentException("비밀번호에 닉네임을 포함할 수 없습니다.");
        }
        if(!userRequestDto.getPassword().equals(userRequestDto.getConfirmPassword())){
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        User user = new User(userRequestDto);
        userRepository.save(user);
    }
}
