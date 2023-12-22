package com.sparta.plusproject.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserRequestDto {
    @Pattern(regexp = "^[a-z0-9]+$")
    @Length(min = 3)
    private String username;
    @Length(min = 4)
    private String password;
    @Length(min = 4)
    private String confirmPassword;
}
