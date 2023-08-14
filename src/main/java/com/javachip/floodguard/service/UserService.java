package com.javachip.floodguard.service;

import com.javachip.floodguard.dto.RegisterRequestDTO;
import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    public boolean checkEmailDuplicate(String userid) { //이메일 중복 체크
        return userRepository.existsByEmail(userid);
    }

    public boolean checkNicknameDuplicate(String username) { //유저이름 중복 체크
        return userRepository.existsByUsername(username);
    }

    public boolean checkPhoneNumberDuplicate(String phoneNumber) {  //휴대전화 중복 체크
        return userRepository.existsByPhonenumber(phoneNumber);
    }
    public boolean checkPhoneNumber(String phoneNumber) {   //휴대전화 형식 체크
        String regex = "^01(?:0|1|[6-9])\\d{4}\\d{4}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phoneNumber);

        if(m.matches()) {
            return true;
        }
        return false;
    }
    public boolean checkEmailLength(String email) {     // 이메일 길이 체크(100자리까지 제한) 최소길이 제한 하지 않는 이유: 최소한의 형식은 맞춰야 하기 때문
        if(email.length() > 100) {
            return true;
        }
        return false;
    }
    public boolean checkUsernameLength(String username) { //유저 이름 이름 체크(4 ~ 50자리까지 제한)
        if(username.length() < 4 || username.length() > 50) {
            return true;
        }
        return false;
    }
    public boolean isValidEmail(String email) { // 이메일 형식 체크
        log.info(email);
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
    public void register(RegisterRequestDTO req) {
        userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

    public User login(LoginRequestDTO req) {
        Optional<User> optionalUser;

        if(!isValidEmail(req.getUserid())) {
            optionalUser = userRepository.findByUsername(req.getUserid());
            log.info("유저네임");
        } else {
            optionalUser = userRepository.findByEmail(req.getUserid());
            log.info("유저아이디");
        }

        if(optionalUser.isEmpty()) {
            log.info("1");
            return null;
        }

        User user = optionalUser.get();

        if(!encoder.matches(req.getPassword(),user.getPassword())) {
            return null;
        }
        return user;
    }
    public User getLoginUserByLoginId(String loginId) {

        Optional<User> optionalUser;

        if(isValidEmail(loginId)) {
            optionalUser = userRepository.findByEmail(loginId);
        } else {
            optionalUser = userRepository.findByUsername(loginId);
        }

        if(loginId == null) return null;

        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }
}
