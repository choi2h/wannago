package com.wannago.member.config;

import com.wannago.member.jwt.CustomAuthenticationEntryPoint;
import com.wannago.member.jwt.JwtAuthenticationFilter;
import com.wannago.member.jwt.JwtTokenResolver;
import com.wannago.member.jwt.TokenProvider;
import com.wannago.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration // 이 클래스가 스프링 설정 클래스임을 의미
@EnableWebSecurity // Spring Security 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final JwtTokenResolver jwtTokenResolver;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    // 비밀번호 암호화 하기 위한 BCrypt 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 보안 필터 체인
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:5173")); // 프론트 도메인
            configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE")); // 허용 HTTP 메서드
            configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
            configuration.setAllowCredentials(true); // 인증정보 포함 허용
            return configuration;
        }));
        http.csrf(AbstractHttpConfigurer::disable); // CSRF 공격 방지 기능 비활성화
        http.formLogin(AbstractHttpConfigurer::disable); // 기본 로그인 폼 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable); // HTTP Basic 인증 비활성화
        http.authorizeHttpRequests(auth -> auth // 경로별 접근 권한 설정
                .requestMatchers(HttpMethod.POST, "/join","/login","/reissue").permitAll()
                .requestMatchers(HttpMethod.GET, "/posts","/post/*","/qnas","/qnas/*").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated());
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 사용하지 않겠다
        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider, memberRepository, jwtTokenResolver), UsernamePasswordAuthenticationFilter.class); // Filter 적용
        http.exceptionHandling(ex -> ex
                // 인증실패
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                // 권한 없음
                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN)));

        return http.build();
    }
}
