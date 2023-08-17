package com.javachip.floodguard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class CreatePinRequestDTO {
    @NotBlank
    private String pos;
    private String comment;
    @NotBlank
    private String coordx;
    @NotBlank
    private String coordy;
}
