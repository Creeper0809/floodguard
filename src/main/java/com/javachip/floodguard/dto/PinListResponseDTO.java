package com.javachip.floodguard.dto;

import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.entity.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter

public class PinListResponseDTO {
    private Long no;
    private String coordx;
    private String coordy;
    private String name;
    private int type;

}
