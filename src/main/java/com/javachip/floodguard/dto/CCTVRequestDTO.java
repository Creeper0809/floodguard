package com.javachip.floodguard.dto;

import lombok.Data;

@Data
public class CCTVRequestDTO {
    public double coordy;
    public double coordx;
    public String videoURL;
    public String name;
}
