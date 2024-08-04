package com.javachip.floodguard.controller;

import com.javachip.floodguard.dto.PinRequestDTO;
import com.javachip.floodguard.dto.RegisterRequestDTO;
import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.entity.Favorite;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.entity.WhiteList;
import com.javachip.floodguard.jwt.JwtTokenUtil;
import com.javachip.floodguard.repository.WhiteListRepository;
import com.javachip.floodguard.response.ListResponse;
import com.javachip.floodguard.response.Response;
import com.javachip.floodguard.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
@CrossOrigin
public class UserController {

    private final UserService userService;

    private final WhiteListRepository whiteListRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @PostMapping("/register")
    public ResponseEntity<String> join(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {

        log.info(registerRequestDTO.getPassword());

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
    public String login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        User user = userService.login(loginRequestDTO);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            return "아이디나 비밀번호가 틀립니다.";
        }

        // 로그인 성공 => Jwt Token 발급

        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
        String userid = user.getUsername();
        String jwtToken = JwtTokenUtil.createToken(userid, secretKey, expireTimeMs);

        //화이트리스트에 추가

        whiteListRepository.save(WhiteList.builder().userid(userid).token(jwtToken).build());

        return jwtToken;
    }

    @PostMapping("/logout")
    public Response logout(@RequestHeader(value = "Authorization") @NotBlank String header) {
        userService.logout(header);
        return Response.success(null);

    }

    @DeleteMapping("/secession")
    public Response secession(@RequestHeader(value = "Authorization") @NotBlank String header) {
        userService.secession(header);
        return Response.success(null);
    }

    @GetMapping("/{uuid}")
    public Response getUserInfo(@PathVariable("uuid") long uuid) {
        var userInfo = userService.getUserByUUID(uuid);
        return Response.success(userInfo);
    }
    @PostMapping("/pin")
    public String registerPin(@RequestHeader(value = "Authorization") @NotBlank String header, @RequestBody PinRequestDTO pinRequest) {
        String token = String.valueOf(header).split(" ")[1];
        String userid = JwtTokenUtil.getLoginUserid(token,secretKey);
        userService.registerPinByUser(userid,pinRequest.getPinId());return ResponseEntity.status(HttpStatus.OK).bo;
    }
    @DeleteMapping("/pin")
    public String deregisterPin(@RequestHeader(value = "Authorization") @NotBlank String header, @PathVariable("no") @Min(0) Long no) {
        String token = String.valueOf(header).split(" ")[1];
        String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);
        return "실패";
    }
    @GetMapping("{userid}/pins")
    public String getAllFavortiePins(@PathVariable("userid") @NotBlank String userid) {
        User loginUser = userService.getUserById(userid);
        return "실패";
    }
}
