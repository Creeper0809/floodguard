package com.javachip.floodguard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javachip.floodguard.dto.MessageDTO;
import com.javachip.floodguard.repository.FavoriteRepository;
import com.javachip.floodguard.repository.PinRepository;
import com.javachip.floodguard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FloodAlertService {
    private final PinRepository pinRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final SmsService smsService;
    public void alert(String pos) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        var alertedPosPin = pinRepository.findAllByalertpos(pos);
        for(var i : alertedPosPin){
            var concernPeople = favoriteRepository.findAllBypinid(i.getId());
            for(var j : concernPeople){
                var person = userRepository.findByid(j.getUserid()).get();
                var message =MessageDTO.builder()
                                .to(person.getPhonenumber())
                                        .content("[홍수 위험]" + i.getPos());
                smsService.sendSms(message.build());
            }
        }
    }
}
