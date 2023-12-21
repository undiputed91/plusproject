package com.sparta.plusproject.post;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class PostRequestDto {
    @Length(max = 500)
    private String title;

    @Length(max = 5000)
    private String contents;
}
