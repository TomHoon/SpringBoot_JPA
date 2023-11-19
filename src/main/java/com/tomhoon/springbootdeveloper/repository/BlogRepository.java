package com.tomhoon.springbootdeveloper.repository;

import com.tomhoon.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
