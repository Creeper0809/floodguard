package com.javachip.floodguard.controller;

import com.javachip.floodguard.dto.RegisterRequestDTO;
import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.jwt.JwtTokenUtil;
import com.javachip.floodguard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    public String join(@RequestBody RegisterRequestDTO registerRequestDTO) {

        //null 값 체크
        if(registerRequestDTO.getEmail() == null) {
            return "이메일이 비었습니다.";
        }

        if(registerRequestDTO.getUsername() == null) {
            return "유저이름이 비었습니다.";
        }

        if(registerRequestDTO.getPassword() == null) {
            return "비밀번호가 비었습니다.";
        }

        if(registerRequestDTO.getPasswordCheck() == null) {
            return "비밀번호 확인란이 비었습니다.";
        }

        if(registerRequestDTO.getPhonenumber() == null) {
            return "휴대폰 번호가 비었습니다.";
        }
        // email 중복 체크
        if(userService.checkEmailDuplicate(registerRequestDTO.getEmail())) {
            return "이메일이 중복됩니다.";
        }
        // 유저 이름 중복 체크
        if(userService.checkNicknameDuplicate(registerRequestDTO.getUsername())) {
            return "유저 이름이 중복됩니다.";
        }
        // 전화번호 중복 체크
        if(userService.checkPhoneNumberDuplicate(registerRequestDTO.getPhonenumber())) {
            return "전화번호가 중복됩니다.";
        }
        // email 형식 체크
        if(!userService.isValidEmail(registerRequestDTO.getEmail())) {
            return "올바른 이메일 형식을 입력해주세요.";
        }
        // 전화번호 형식 체크
        if(!userService.checkPhoneNumber(registerRequestDTO.getPhonenumber())) {
            return "전화번호 형식이 올바르지 않습니다.";
        }
        // email 길이 체크
        if(userService.checkEmailLength(registerRequestDTO.getEmail())) {
            return "이메일은 최대 200자리 까지입니다.";
        }
        // username 길이 체크
        if(userService.checkUsernameLength(registerRequestDTO.getUsername())) {
            return "유저이름은 최소 4자리 ~ 최대 50자리까지 입니다.";
        }
        // password와 passwordCheck가 같은지 체크
        if(!registerRequestDTO.getPassword().equals(registerRequestDTO.getPasswordCheck())) {
            return"바밀번호가 일치하지 않습니다.";
        }

        userService.register(registerRequestDTO);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {

        User user = userService.login(loginRequestDTO);

        String userid;

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            return"로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }

        // 로그인 성공 => Jwt Token 발급

        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
        userid = user.getUsername();
        String jwtToken = JwtTokenUtil.createToken(userid, secretKey, expireTimeMs);

        return jwtToken;
    }
    @GetMapping("/info")
    public String userInfo(@RequestHeader(value = "Authorization") String header) {

        String token = String.valueOf(header).split(" ")[1];
        String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);
        User loginUser = userService.getLoginUserByLoginId(finduser);

        return String.format("loginId : %s\nnickname : %s\nrole : %s",
                loginUser.getId(), loginUser.getUsername(), loginUser.getRole().name());
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
    @GetMapping("/test")
    public String testPage(){return "ㅁㄴㅇㅁㄴㅇ";}
}
