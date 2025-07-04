package com.wannago.member.jwt;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 요청마다 AccessToken 검사
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final JwtTokenResolver jwtTokenResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // 인증 필요 없는 경로 (회원가입, 로그인 등)
        if (path.equals("/join") || path.equals("/login") || path.equals("/reissue")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenResolver.resolveAccessToken(request);

        // 토큰 없으면 인증 실패 예외 던짐
        if (!StringUtils.hasText(token)) {
            throw new BadCredentialsException("유효하지 않은 토큰");
        }

        // 토큰 유효하지 않으면 예외 던짐
        if (!tokenProvider.validateToken(token)) {
            throw new BadCredentialsException("유효하지 않은 토큰");
        }

        try {
            String loginId = tokenProvider.getLoginIdFromToken(token);
            Member member = memberRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member, null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            throw new CustomException(CustomErrorCode.INVALID_TOKEN);
        }

        filterChain.doFilter(request, response);
    }
}
