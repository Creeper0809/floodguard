package com.javachip.floodguard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class CreatePinRequestDTO {
    private String pos;
    private String comment;
    private String coordx;
    private String coordy;
}
