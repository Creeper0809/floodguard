package com.javachip.floodguard.service;


import com.javachip.floodguard.dto.FavoriteDTO;
import com.javachip.floodguard.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    public List<FavoriteDTO> getAllFavoritePins(Long userid){
        List<FavoriteDTO> result = new ArrayList<>();
        var arr = favoriteRepository.findAllByUserid(userid);
        for(var i : arr){
            FavoriteDTO temp = new FavoriteDTO();
            temp.setUserid(i.getUserid());
            temp.setPinid(i.getPinid());
            result.add(temp);
        }
        return result;
    }
}
