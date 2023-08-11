package com.javachip.floodguard.service;

import com.javachip.floodguard.dto.JoinRequest;
import com.javachip.floodguard.dto.LoginRequest;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;


    public boolean checkLoginIdDuplicate(String userid) { //로그인 중복 체크
        return userRepository.existsByUserid(userid);
    }

    public boolean checkNicknameDuplicate(String username) { //유저이름 중복 체크
        return userRepository.existsByUsername(username);
    }

    public void join(JoinRequest req) { // 회원가입 : dto로 받아서 entity로 변환해서 db에 저장
        userRepository.save(req.toEntity());
    }

    public void join2(JoinRequest req) {
        userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByUserid(req.getUserid());
        log.info(optionalUser.toString());

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
    /*
    public User getLoginUserById(Long userId) {
            if(userId == null) return null;

            Optional<User> optionalUser = userRepository.findById(userId);
            if(optionalUser.isEmpty()) return null;

            return optionalUser.get();
    }
    */
    public User getLoginUserByLoginId(String loginId) {
        if(loginId == null) return null;

        Optional<User> optionalUser = userRepository.findByUserid(loginId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }
}