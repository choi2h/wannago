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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final JwtTokenResolver jwtTokenResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenResolver.resolveAccessToken(request);

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response); // 이건 허용할 수도 있음 (비로그인 가능 API의 경우)
            return;
        }

        if (!tokenProvider.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return;
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
