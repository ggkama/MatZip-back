package com.kh.matzip.configuration.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.matzip.member.model.dao.MemberMapper;
import com.kh.matzip.member.model.dto.MemberDTO;
import com.kh.matzip.member.util.JWTUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	
	private final JWTUtil jwtUtil;
    private final MemberMapper memberMapper;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);

            try {
                Claims claims = jwtUtil.parseJwt(token);
                String userId = claims.getSubject();
                String userRole = jwtUtil.extractUserRole(token);
                
                MemberDTO member = memberMapper.findByUserId(userId);
                
                if (member == null) {
                    log.warn("존재하지 않는 회원 접근 시도: {}", userId);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("존재하지 않는 회원입니다.");
                    return;
                }

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                	
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                    UsernamePasswordAuthenticationToken authentication =
                    		new UsernamePasswordAuthenticationToken(
                                    userDetails, null, Collections.singletonList(new SimpleGrantedAuthority(userRole)));

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.debug("인증 성공: userId={}, role={}", userId, userRole);
                }

            } catch (ExpiredJwtException e) {
                log.warn("JWT 만료됨: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("토큰이 만료되었습니다. 다시 로그인하세요.");
                return;
            } catch (Exception e) {
                log.error("JWT 필터 처리 오류: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("유효하지 않은 토큰입니다.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}