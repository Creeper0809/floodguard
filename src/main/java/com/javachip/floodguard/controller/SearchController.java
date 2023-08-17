package com.javachip.floodguard.controller;

import com.javachip.floodguard.dto.SearchPostRequestDTO;
import com.javachip.floodguard.response.ListResponse;
import com.javachip.floodguard.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
@Slf4j
@CrossOrigin
public class SearchController {
    private final SearchService searchService;
    @PostMapping("/do")
    public void addSearch(@RequestBody @Valid SearchPostRequestDTO searchPostRequestDTO){
        searchService.addSearch(searchPostRequestDTO);
    }
    @GetMapping("/info")
    public ListResponse getSearch(){
        return ListResponse.success(searchService.getRank());
    }
}
