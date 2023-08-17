package com.javachip.floodguard.service;

import com.javachip.floodguard.entity.Satisfaction;
import com.javachip.floodguard.repository.SatisfactionRepository;
import com.javachip.floodguard.dto.SatisfactionRequestDTO;
import com.javachip.floodguard.dto.SatisfactionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SatisfactionService {

    private final SatisfactionRepository satisfactionRepository;

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