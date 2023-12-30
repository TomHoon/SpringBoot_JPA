package com.tomhoon.springbootdeveloper.config.jwt;

import com.tomhoon.springbootdeveloper.domain.User;
import com.tomhoon.springbootdeveloper.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    // GenerateToken 검증
    @DisplayName("generateToken(): 유저정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("만료된 토큰일 때에 실패하는 테스트")
    @Test
    void validTOken_invalidToken() {
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        boolean result = tokenProvider.validToken(token);
        assertThat(result).isFalse();
    }

    @DisplayName("유효한 토큰 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);
        boolean result = tokenProvider.validToken(token);
        assertThat(result).isTrue();
    }

    @DisplayName("토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        String email = "tomhoon@gmail.com";
        String token = JwtFactory.builder()
                .subject(email)
                .build()
                .createToken(jwtProperties);

        System.out.println("token > " +  token);
        Authentication authentication = tokenProvider.getAuthentication(token);
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(email);
    }

    @DisplayName("token으로부터 userId 값을 가져온다.")
    @Test
    void getUserId() {
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);
        Long userIdByToken = tokenProvider.getUserId(token);
        assertThat(userIdByToken).isEqualTo(userId);
    }
}
