package com.javachip.floodguard.api.satisfaction;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/satisfactions")
public class SatisfactionController {

    private final SatisfactionService satisfactionService;

    public SatisfactionController(SatisfactionService satisfactionService) {
        this.satisfactionService = satisfactionService;
    }

    @PostMapping
    public SatisfactionResponseDTO submitSatisfaction(@RequestBody SatisfactionRequestDTO requestDTO) {
        return satisfactionService.submitSatisfaction(requestDTO);
    }

    @GetMapping("/average")
    public double getAverageSatisfaction() {
        return satisfactionService.calculateAverageSatisfaction();
    }
}
