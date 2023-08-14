package com.javachip.floodguard.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PinMoreInfoResponseDTO {
    private String url;
    private String comment;
}
