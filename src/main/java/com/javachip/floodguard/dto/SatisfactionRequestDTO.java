package com.javachip.floodguard.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SatisfactionRequestDTO {
    @Min(0)
    @Max(5)
    private double rating;
}
