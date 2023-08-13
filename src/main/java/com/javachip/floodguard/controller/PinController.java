package com.javachip.floodguard.controller;

import com.javachip.floodguard.dto.LoginRequestDTO;
import com.javachip.floodguard.dto.PinListResponseDTO;
import com.javachip.floodguard.response.ListResponse;
import com.javachip.floodguard.response.Response;
import com.javachip.floodguard.service.PinService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pins")
@Slf4j
public class PinController {
    private final PinService pinService;
    @GetMapping("/pin")
    public ListResponse<PinListResponseDTO> getAllPin(){
        var arr = pinService.getAllPins();
        return ListResponse.success(arr);
    }
    @PostMapping("/pin")
    public Response createPin(@RequestBody LoginRequestDTO loginRequestDTO){
        return Response.success(null);
    }
}
