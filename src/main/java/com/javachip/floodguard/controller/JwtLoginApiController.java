package com.javachip.floodguard.controller;

import com.javachip.floodguard.dto.JoinRequest;
import com.javachip.floodguard.dto.LoginRequest;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.jwt.JwtTokenUtil;
import com.javachip.floodguard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class JwtLoginApiController {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @PostMapping("/register")
    public String join(@RequestBody JoinRequest joinRequest) {
        log.info(joinRequest.getUserid());
        log.info(joinRequest.getPassword());

        // loginId 중복 체크
        if(userService.checkLoginIdDuplicate(joinRequest.getUserid())) {
            return "로그인 아이디가 중복됩니다.";
        }
        // 닉네임 중복 체크
        if(userService.checkNicknameDuplicate(joinRequest.getUsername())) {
            return "유저 이름이 중복됩니다.";
        }
        // password와 passwordCheck가 같은지 체크
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            return"바밀번호가 일치하지 않습니다.";
        }

        userService.join2(joinRequest);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        User user = userService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            return"로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }

        // 로그인 성공 => Jwt Token 발급

        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        String jwtToken = JwtTokenUtil.createToken(user.getUserid(), secretKey, expireTimeMs);

        return jwtToken;
    }

    @GetMapping("/info")
    public String userInfo(@AuthenticationPrincipal User user) {
        User loginUser = userService.getLoginUserByLoginId(user.getUserid());

        return String.format("loginId : %s\nnickname : %s\nrole : %s",
                loginUser.getId(), loginUser.getUsername(), loginUser.getRole().name());
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
}