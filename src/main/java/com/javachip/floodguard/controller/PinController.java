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
    @Value("${jwt.secret}")
    private String secretKey;
    @GetMapping("/")
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
    @PostMapping("/")
    public Response createPin(@RequestBody @Valid CreatePinRequestDTO createPinRequestDTO, @RequestHeader(value = "Authorization",required = false) @NotBlank String header){
        if (header == null)
            return Response.error(400);

        String token = String.valueOf(header).split(" ")[1];
        String finduser = JwtTokenUtil.getLoginUserid(token,secretKey);
        var i = pinService.createUserPin(createPinRequestDTO,finduser);
        return Response.success(null);
    }
    @DeleteMapping("/")
    public Response deletePin(@PathVariable("no") @Min(0) Long no){
        pinService.removePin(no);
        return Response.success("");
    }
    @GetMapping("/{no}")
    public Response getPin(@PathVariable("no") @Min(0) Long no){
        var arr = pinService.getPinInfo(no);
        return Response.success(arr);
    }
}
