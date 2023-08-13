package com.javachip.floodguard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javachip.floodguard.api.FloodAlertAPI;
import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.response.Response;
import com.javachip.floodguard.service.FloodAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert")
@Slf4j
public class FloodAlert {
    private final FloodAlertService floodAlertService;
    @PostMapping("/favoritepin")
    public Response createFavoritePin(){
        return Response.success(null);
    }
    @GetMapping("favoritepin")
    public Response getFavoritePin(){
        return Response.success(null);
    }
    @GetMapping("/{pos}")
    public Response testFloodAlert(@PathVariable("pos")String pos) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        floodAlertService.alert(pos);
        return Response.success(null);
    }
}
