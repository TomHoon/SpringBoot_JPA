package com.tomhoon.springbootdeveloper.dto;

import com.tomhoon.springbootdeveloper.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드값 생성자
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(){
        return new Article(title, content);
    }
}
