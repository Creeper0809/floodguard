package com.javachip.floodguard.dto;

import lombok.Data;

@Data
public class FloodAPIRequestDTO {
    String where;
    String kind;
    String date;
}
