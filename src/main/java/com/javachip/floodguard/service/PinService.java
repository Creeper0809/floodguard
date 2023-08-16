package com.javachip.floodguard.service;

import com.javachip.floodguard.api.CCTVApi;
import com.javachip.floodguard.dto.CreatePinRequestDTO;
import com.javachip.floodguard.dto.PinListResponseDTO;
import com.javachip.floodguard.dto.PinMoreInfoResponseDTO;
import com.javachip.floodguard.entity.Pin;
import com.javachip.floodguard.repository.PinRepository;
import com.javachip.floodguard.repository.UserRepository;
import com.javachip.floodguard.response.ListResponse;
import com.javachip.floodguard.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@RequiredArgsConstructor
@Service
public class PinService {
    private final PinRepository pinRepository;
    private final CCTVApi cctv;
    public List<PinListResponseDTO> getAllPins(){
        List<PinListResponseDTO> result = new ArrayList<>();
        var arr = pinRepository.findAllByuserid("root");
        for(var i : arr){
            PinListResponseDTO temp = new PinListResponseDTO();
            temp.setNo(i.getId());
            temp.setCoordy(i.getCoordy());
            temp.setCoordx(i.getCoordx());
            temp.setName(i.getPos());
            temp.setType(i.getType());
            result.add(temp);
        }
        return result;
    }
    public List<PinListResponseDTO> getAllPinsWithUserid(String user){
        List<PinListResponseDTO> result = new ArrayList<>();
        var arr = pinRepository.findAllByuserid(user);
        for(var i : arr){
            PinListResponseDTO temp = new PinListResponseDTO();
            temp.setNo(i.getId());
            temp.setCoordy(i.getCoordy());
            temp.setCoordx(i.getCoordx());
            temp.setName(i.getPos());
            temp.setType(i.getType());
            result.add(temp);
        }
        return result;
    }
    public void createCCTVPin(){
        String[][] arr = {
                {
                        "서울시(광진교)","37.54438996824168","127.11436739783055"
                },
                {
                        "서울시(너부대교)","37.54438996824168","127.11436739783055"
                },
                {
                        "서울시(대곡교)","37.08794894312624","128.66757199420692"
                },
                {
                        "서울시(대치교)","37.08794894312624","128.66757199420692"
                },
                {
                        "서울시(신대방1교)","37.487782730685176","126.91265250562651"
                },
                {
                        "서울시(신림5교)","37.487782730685176","126.91265250562651"
                },
                {
                        "서울시(오금교)","37.506579354266066","127.12970252997002"
                },
                {
                        "서울시(월계2교)","37.506579354266066","127.12970252997002"
                },
                {
                        "서울시(잠수교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(중랑교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(창동교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(청담대교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(한강대교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(행주대교)", "37.51169112488922", "126.98851961329397"
                }
        };
        //서울 좌표
        var result = cctv.getCCTV("126.734086","37.413294","127.269311","37.715133");
        List<Pin> toSavePin = new ArrayList<>();
        for(var i : result){
            if(pinRepository.existsBypos(i.name)){
                continue;
            }
            double sort = Double.MAX_VALUE;
            String temp = "";
            for(var j : arr){
                double distance = cctv.getDistance(i.coordx,j[2],i.coordy,j[1]);
                if(distance < sort){
                    sort = distance;
                    temp = j[0];
                }
            }
            Pin tempPin = new Pin();
            tempPin.setAlertpos(temp);
            tempPin.setPos(i.name);
            tempPin.setType(0);
            tempPin.setCoordx(Double.toString(i.coordx));
            tempPin.setCoordy(Double.toString(i.coordy));
            toSavePin.add(tempPin);
        }
        pinRepository.saveAll(toSavePin);
    }
    public PinMoreInfoResponseDTO getPinInfo(Long no){
        var pin = pinRepository.findById(no).get();
        var response = new PinMoreInfoResponseDTO();
        if(no < 200){
            double x = Double.parseDouble(pin.getCoordx());
            double y = Double.parseDouble(pin.getCoordy());
            var result = cctv.getCCTV(Double.toString(x - 0.001),Double.toString(y - 0.001),Double.toString(x + 0.001),Double.toString(y + 0.001));
            if(result.size() == 0){
                return null;
            }
            for(var i : result) {
                if (i.getName().equals(pin.getPos())) {
                    response.setUrl(result.get(0).getVideoURL());
                    response.setComment(result.get(0).getName() + "의 영상");
                    break;
                }
            }
        }
        else {
            response.setComment(pin.getComment());
        }
        return response;
    }
    public Pin getPin(Long id){
        return pinRepository.findById(id).get();
    }
    public void removePin(Long id){
        pinRepository.delete(pinRepository.findById(id).get());
    }
    public Pin createUserPin(CreatePinRequestDTO createPinRequestDTO,String user){
        Pin pin = new Pin();
        pin.setPos(createPinRequestDTO.getPos());
        pin.setCoordy(createPinRequestDTO.getCoordy());
        pin.setCoordx(createPinRequestDTO.getCoordx());
        pin.setComment(createPinRequestDTO.getComment());
        pin.setType(2);
        pin.setUserid(user);
        String[][] arr = {
                {
                        "서울시(광진교)","37.54438996824168","127.11436739783055"
                },
                {
                        "서울시(너부대교)","37.54438996824168","127.11436739783055"
                },
                {
                        "서울시(대곡교)","37.08794894312624","128.66757199420692"
                },
                {
                        "서울시(대치교)","37.08794894312624","128.66757199420692"
                },
                {
                        "서울시(신대방1교)","37.487782730685176","126.91265250562651"
                },
                {
                        "서울시(신림5교)","37.487782730685176","126.91265250562651"
                },
                {
                        "서울시(오금교)","37.506579354266066","127.12970252997002"
                },
                {
                        "서울시(월계2교)","37.506579354266066","127.12970252997002"
                },
                {
                        "서울시(잠수교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(중랑교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(창동교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(청담대교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(한강대교)","37.51169112488922","126.98851961329397"
                },
                {
                        "서울시(행주대교)", "37.51169112488922", "126.98851961329397"
                }
        };
        double sort = Double.MAX_VALUE;
        String temp = "";
        for(var j : arr){
            double distance = cctv.getDistance(createPinRequestDTO.getCoordx(),j[2],createPinRequestDTO.getCoordy(),j[1]);
            if(distance < sort){
                sort = distance;
                temp = j[0];
            }
        }
        pin.setAlertpos(temp);
        return pinRepository.save(pin);
    }
}
