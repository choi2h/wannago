package com.wannago.member.config;

import java.util.List;

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

import com.wannago.member.jwt.CustomAuthenticationEntryPoint;
import com.wannago.member.jwt.JwtAuthenticationFilter;
import com.wannago.member.jwt.JwtTokenResolver;
import com.wannago.member.jwt.TokenProvider;
import com.wannago.member.repository.MemberRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final JwtTokenResolver jwtTokenResolver;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:5173"));
            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            // [개선] Authorization 헤더를 명시적으로 허용하는 것이 더 안전합니다.
            configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
            configuration.setAllowCredentials(true);
            return configuration;
        }));
        
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/join", "/login", "/reissue", "/check-email").permitAll()
                .requestMatchers(HttpMethod.GET, "/posts", "/post", "/posts/*", "/post/*", "/post/**", "/posts/**", "/qna/**", "/qnas", "/qnas/*").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // [추가된 핵심 코드]
                // /mypage/로 시작하는 모든 경로는 반드시 인증이 필요하다고 명시합니다.
                .requestMatchers("/mypage/**").authenticated()
                
                // 기존 규칙은 그대로 유지하여, 다른 인증이 필요한 경로들도 문제없이 동작하게 합니다.
                .anyRequest().authenticated()
        );
        
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                
        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider, memberRepository, jwtTokenResolver), UsernamePasswordAuthenticationFilter.class);
        
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN)));

        return http.build();
    }
}