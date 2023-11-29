package com.javachip.floodguard.service;

import com.javachip.floodguard.dto.PinRequestDTO;
import com.javachip.floodguard.dto.RegisterRequestDTO;
import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.dto.UserinfoDTO;
import com.javachip.floodguard.entity.BlackList;
import com.javachip.floodguard.entity.Favorite;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.entity.WhiteList;
import com.javachip.floodguard.jwt.JwtTokenUtil;
import com.javachip.floodguard.repository.BlackListRepository;
import com.javachip.floodguard.repository.FavoriteRepository;
import com.javachip.floodguard.repository.UserRepository;
import com.javachip.floodguard.repository.WhiteListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private final BlackListRepository blackListRepository;

    private final WhiteListRepository whiteListRepository;

    private final FavoriteRepository favoriteRepository;

    @Value("${jwt.secret}")
    private String secretKey;
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

    public void logout(String header) {

        // userid 가져오기
        String token = String.valueOf(header).split(" ")[1];
        String userid = JwtTokenUtil.getLoginUserid(token,secretKey);

        //화이트리스트에서 userid로 정보 조회(username 조회)
        WhiteList deleteuser = whiteListRepository.findByUserid(userid);    // (userid = username)

        //화이트리스트에서 삭제
        whiteListRepository.delete(deleteuser);

        //블랙리스트에 추가
        blackListRepository.save(BlackList.builder().userid(deleteuser.getUserid()).token(deleteuser.getToken()).build());
    }

    public void secession(String header) {

        // userid 가져오기
        String token = String.valueOf(header).split(" ")[1];
        String userid = JwtTokenUtil.getLoginUserid(token,secretKey);

        //화이트리스트에서 userid로 정보 조회(username 조회)
        WhiteList deleteuser = whiteListRepository.findByUserid(userid);    // (userid = username)

        //화이트리스트에서 삭제
        whiteListRepository.delete(deleteuser);

        //유저에서 삭제
        userRepository.delete(userRepository.findAllByUsername(deleteuser.getUserid()));

        //블랙리스트에 추가
        blackListRepository.save(BlackList.builder().userid(deleteuser.getUserid()).token(deleteuser.getToken()).build());

    }
    /**
     * ID를 통해 userinfo를 가져옴
     *
     * @author 임용헌
     * @param id 아이디
     * @return 유저 정보
     */
    public User getUserById(String id) {

        Optional<User> optionalUser;

        if(id == null) return null;

        if(isValidEmail(id)) {
            optionalUser = userRepository.findByEmail(id);
        } else {
            optionalUser = userRepository.findByUsername(id);
        }

        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    /**
     * UUID를 통해 userinfo를 가져옴
     *
     * @author 임용헌
     * @param uuid 유저의 고유 번호
     * @return 유저 정보
     */
    public UserinfoDTO getUserByUUID(long uuid){
        var user = userRepository.findById(uuid).get();
        return new UserinfoDTO(user);
    }

    public void registerPinByUser(String userid, long pinId){
        var user = getUserById(userid);
        if(favoriteRepository.existsByUseridAndPinid(user.getId(),pinId)){
            // 존재함 예외 던지기
            return;
        }
        var favorite = new Favorite().builder()
                .userid(user.getId())
                .pinid(pinId)
                .build();
        favoriteRepository.save(favorite);
    }
}
