package com.javachip.floodguard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FloodAlertService {
    private final PinRepository pinRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    public void alert(String pos , String kind) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        var alertedPosPin = pinRepository.findAllByalertpos(pos);
        HashMap<String, List<String>> alert = new HashMap<>();
        for(var i : alertedPosPin){
            var concernPeople = favoriteRepository.findAllBypinid(i.getId());
            for(var j : concernPeople){
                var person = userRepository.findById(j.getUserid()).get();
                if(!alert.containsKey(person.getUsername())){
                    alert.put(person.getUsername(),new ArrayList<>());
                    alert.get(person.getUsername()).add(person.getPhonenumber());
                }
                alert.get(person.getUsername()).add(i.getPos());
            }
        }
        for(var i : alert.keySet()){
            var person = alert.get(i);
            var content = new StringBuilder("[홍수 위험]\n"+person.get(1));
            if(person.size() > 2){
                content.append("지역 외 " + (person.size()-2) +"곳에서");
            }
            else{
                content.append("지역에서");
            }
            content.append(" 홍수 위험이 있습니다.\n안전에 유의하세요.\n"+kind);
            System.out.println(content.toString());
        }
    }
}
