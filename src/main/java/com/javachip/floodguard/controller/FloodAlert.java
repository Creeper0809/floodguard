package com.javachip.floodguard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javachip.floodguard.api.FloodAlertAPI;
import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.response.Response;
import com.javachip.floodguard.service.FloodAlertService;
import jakarta.validation.constraints.NotBlank;
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
@CrossOrigin
public class FloodAlert {
    private final FloodAlertService floodAlertService;
    @GetMapping("/{pos}")
    public Response testFloodAlert(@PathVariable("pos") @NotBlank String pos) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        floodAlertService.alert(pos,"테스트 경보");
        return Response.success(null);
    }
}
