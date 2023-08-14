package com.javachip.floodguard.controller;

import com.javachip.floodguard.dto.RegisterRequestDTO;
import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.jwt.JwtTokenUtil;
import com.javachip.floodguard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> join(@RequestBody RegisterRequestDTO registerRequestDTO) {

        //null 값 체크
        if (registerRequestDTO.getEmail() == "") {
            return ResponseEntity.status(300).build();
        }
        if(registerRequestDTO.getUsername() == "") {
            return ResponseEntity.status(301).build();
        }
        if(registerRequestDTO.getPassword() == "") {
            return ResponseEntity.status(302).build();
        }
        if(registerRequestDTO.getPasswordCheck() == "") {
            return ResponseEntity.status(303).build();
        }
        if(registerRequestDTO.getPhonenumber() == "") {
            return ResponseEntity.status(304).build();
        }
        // email 중복 체크
        if(userService.checkEmailDuplicate(registerRequestDTO.getEmail())) {
            return ResponseEntity.status(305).build();
        }
        // 유저 이름 중복 체크
        if(userService.checkNicknameDuplicate(registerRequestDTO.getUsername())) {
            return ResponseEntity.status(306).build();
        }
        // 전화번호 중복 체크
        if(userService.checkPhoneNumberDuplicate(registerRequestDTO.getPhonenumber())) {
            return ResponseEntity.status(307).build();
        }
        // email 형식 체크
        if(!userService.isValidEmail(registerRequestDTO.getEmail())) {
            return ResponseEntity.status(308).build();
        }
        // 전화번호 형식 체크
        if(!userService.checkPhoneNumber(registerRequestDTO.getPhonenumber())) {
            return ResponseEntity.status(309).build();
        }
        // email 길이 체크
        if(userService.checkEmailLength(registerRequestDTO.getEmail())) {
            return ResponseEntity.status(310).build();
        }
        // username 길이 체크
        if(userService.checkUsernameLength(registerRequestDTO.getUsername())) {
            return ResponseEntity.status(311).build();
        }
        // password와 passwordCheck가 같은지 체크
        if(!registerRequestDTO.getPassword().equals(registerRequestDTO.getPasswordCheck())) {
            return ResponseEntity.status(312).build();
        }

        userService.register(registerRequestDTO);
        return ResponseEntity.status(200).build();

}
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {

        User user = userService.login(loginRequestDTO);

        String userid;

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            return "아이디나 비밀번호가 틀립니다.";
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
