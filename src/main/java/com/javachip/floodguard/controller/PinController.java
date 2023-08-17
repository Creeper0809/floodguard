package com.javachip.floodguard.controller;

import com.javachip.floodguard.dto.*;
import com.javachip.floodguard.entity.Favorite;
import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.jwt.JwtTokenUtil;
import com.javachip.floodguard.repository.FavoriteRepository;
import com.javachip.floodguard.response.ListResponse;
import com.javachip.floodguard.response.Response;
import com.javachip.floodguard.service.FavoriteService;
import com.javachip.floodguard.service.ImageAnalysisSevice;
import com.javachip.floodguard.service.PinService;
import com.javachip.floodguard.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            System.out.println(finduser + "님의 핀을 가져오기");
            var arr = pinService.getAllPinsWithUserid(finduser);
            return ListResponse.success(arr);
        } else {
            var arr = pinService.getAllPins();
            return ListResponse.success(arr);
        }
    }
    @PostMapping("/pin")
    public Response createPin(@RequestBody @Valid CreatePinRequestDTO createPinRequestDTO, @RequestHeader(value = "Authorization",required = false) @NotBlank String header){
        if (header == null) {
            System.out.println(createPinRequestDTO.getPos());
        }
        else{
            String token = String.valueOf(header).split(" ")[1];
            String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);
            var i = pinService.createUserPin(createPinRequestDTO,finduser);
            System.out.println(i.getId());
            return Response.success(i.getId());
        }
        return Response.success(null);
    }
    @DeleteMapping("/pin/{no}")
    public Response deletePin(@PathVariable("no") @Min(0) Long no){
        pinService.removePin(no);
        return Response.success("");
    }
    @GetMapping("/pin/{no}")
    public Response getPin(@PathVariable("no") @Min(0) Long no){
        var arr = pinService.getPinInfo(no);
        return Response.success(arr);
    }
    @GetMapping("/pin/register/{no}")
    public String pinRegister(@RequestHeader(value = "Authorization") @NotBlank String header, @PathVariable("no") @Min(0) Long no) {
        String token = String.valueOf(header).split(" ")[1];
        String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);
        User loginUser = userService.getLoginUserByLoginId(finduser);
        if(loginUser == null){
            return "없는 유저입니다";
        }
        Optional<Favorite> f = favoriteRepository.findBypinidAndUserid(no,loginUser.getId());
        if(f.isPresent()){
            favoriteRepository.delete(f.get());
        }
        else{
            favoriteRepository.save(favoriteDTO.toEntity(no,loginUser.getId()));
        }
        return "관심사가 등록되었습니다.";
    }
    @GetMapping("/pin/pinlist/{userid}")
    public ListResponse getAllFavortiePins(@PathVariable("userid") @NotBlank String userid) {

        User loginUser = userService.getLoginUserByLoginId(userid);
        if(loginUser == null){
            return ListResponse.error(401);
        }
        var arr = favoriteService.getAllFavoritePins(loginUser.getId());
        return ListResponse.success(arr);
    }
}
