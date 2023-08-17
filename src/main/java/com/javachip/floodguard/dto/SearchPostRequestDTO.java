package com.javachip.floodguard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchPostRequestDTO {
    @NotBlank
    private String searchval;
}
