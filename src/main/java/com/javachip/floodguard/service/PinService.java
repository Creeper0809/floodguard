package com.javachip.floodguard.service;

import com.javachip.floodguard.dto.PinListResponseDTO;
import com.javachip.floodguard.repository.PinRepository;
import com.javachip.floodguard.repository.UserRepository;
import com.javachip.floodguard.response.ListResponse;
import com.javachip.floodguard.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PinService {
    private final PinRepository pinRepository;
    public List<PinListResponseDTO> getAllPins(){
        List<PinListResponseDTO> result = new ArrayList<>();
        var arr = pinRepository.findAll();
        for(var i : arr){
            PinListResponseDTO temp = new PinListResponseDTO();
            temp.setNo(i.getId());
            temp.setCoordy(i.getCoordy());
            temp.setCoordx(i.getCoordx());
            result.add(temp);
        }
        return result;
    }
}
