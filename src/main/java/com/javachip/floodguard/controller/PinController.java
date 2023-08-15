package com.javachip.floodguard.controller;

import com.javachip.floodguard.dto.*;
import com.javachip.floodguard.entity.Favorite;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.jwt.JwtTokenUtil;
import com.javachip.floodguard.repository.FavoriteRepository;
import com.javachip.floodguard.response.ListResponse;
import com.javachip.floodguard.response.Response;
import com.javachip.floodguard.service.FavoriteService;
import com.javachip.floodguard.service.PinService;
import com.javachip.floodguard.service.UserService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pins")
@Slf4j
@CrossOrigin
public class PinController {
    private final PinService pinService;
    private final UserService userService;
    private final FavoriteService favoriteService;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteDTO favoriteDTO;

    @Value("${jwt.secret}")
    private String secretKey;

    @GetMapping("/pin")
    public ListResponse<PinListResponseDTO> getAllPin(@RequestHeader(value = "Authorization",required = false) String header){
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.split(" ")[1];
            String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);
            var arr = pinService.getAllPinsWithUserid(finduser);
            return ListResponse.success(arr);
        } else {
            var arr = pinService.getAllPins();
            return ListResponse.success(arr);
        }
    }
    @PostMapping("/pin")
    public Response createPin(@RequestBody CreatePinRequestDTO createPinRequestDTO, @RequestHeader(value = "Authorization",required = false) String header){
        if (header == null) {
            System.out.println(createPinRequestDTO.getPos());
        }
        else{
            String token = String.valueOf(header).split(" ")[1];
            String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);
        }
        return Response.success(null);
    }
    @GetMapping("/pin/{no}")
    public Response<PinMoreInfoResponseDTO> getPin(@PathVariable("no")Long no){
        var arr = pinService.getPinInfo(no);
        return Response.success(arr);
    }
    @PostMapping("/pin/register/{no}")
    public String pinRegister(@RequestHeader(value = "Authorization") String header, @PathVariable("no") Long no) {

        String token = String.valueOf(header).split(" ")[1];
        String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);
        User loginUser = userService.getLoginUserByLoginId(finduser);
        favoriteRepository.save(favoriteDTO.toEntity(no,loginUser.getId()));

        return "관심사가 등록되었습니다.";
    }
    @GetMapping("/pin/pinlist/{userid}")
    public ListResponse<FavoriteDTO> getAllFavortiePins(@PathVariable("userid") String userid) {
        User loginUser = userService.getLoginUserByLoginId(userid);
        var arr = favoriteService.getAllFavoritePins(loginUser.getId());
        return ListResponse.success(arr);
    }
}
