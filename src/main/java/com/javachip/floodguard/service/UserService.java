package com.javachip.floodguard.service;

import com.javachip.floodguard.dto.RegisterRequestDTO;
import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public boolean checkEmailOrUsername(String userid) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
        "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userid);
        if(m.matches()) {
            return true;
        } else {
            return false;
        }
    }


    public void register(RegisterRequestDTO req) {
        userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

    public User login(LoginRequestDTO req) {
        Optional<User> optionalUser;

        if(checkEmailOrUsername(req.getUserid())) {
            optionalUser = userRepository.findByEmail(req.getUserid());
        } else {
            optionalUser = userRepository.findByUsername(req.getUserid());
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

        if(checkEmailOrUsername(loginId)) {
            optionalUser = userRepository.findByEmail(loginId);
        } else {
            optionalUser = userRepository.findByUsername(loginId);
        }

        if(loginId == null) return null;

        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }
}
