package com.javachip.floodguard.jwt;

import com.javachip.floodguard.entity.BlackList;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.repository.BlackListRepository;
import com.javachip.floodguard.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {


    private final UserService userService;

    private final String secretKey;

    private final BlackListRepository blackListRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
        if(!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];

        String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);

        List<BlackList> blackListOptional = blackListRepository.findAllByUserid(finduser);

        // 전송받은 Jwt Token이 블랙리스트에 있으면 다음 필터 진행(인증x)
        if(blackListOptional.equals(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 Jwt Token이 만료되었으면 => 401을 클라이언트에 보냄
        if (JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt Token에서 loginId 추출
        String loginId = JwtTokenUtil.getLoginUserid(token, secretKey);

        // 추출한 loginId로 User 찾아오기
        User loginUser = userService.getUserById(loginId);

        if(loginUser == null){
            return;
        }
        // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getId(),  null, List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
