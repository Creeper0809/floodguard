package com.javachip.floodguard.api.satisfaction;

import org.springframework.stereotype.Service;

@Service
public class SatisfactionService {

    private final SatisfactionRepository satisfactionRepository;

    public SatisfactionService(SatisfactionRepository satisfactionRepository) {
        this.satisfactionRepository = satisfactionRepository;
    }

    public SatisfactionResponseDTO submitSatisfaction(SatisfactionRequestDTO requestDTO) {
        Satisfaction satisfaction = new Satisfaction();
        satisfaction.setRating(requestDTO.getRating());

        satisfaction = satisfactionRepository.save(satisfaction);

        SatisfactionResponseDTO responseDTO = new SatisfactionResponseDTO();
        responseDTO.setId(satisfaction.getId());
        responseDTO.setRating(satisfaction.getRating());

        return responseDTO;
    }

    public double calculateAverageSatisfaction() {
        return satisfactionRepository.findAll().stream()
                .mapToDouble(Satisfaction::getRating)
                .average()
                .orElse(0.0);
    }
}