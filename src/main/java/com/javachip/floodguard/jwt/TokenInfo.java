package com.javachip.floodguard.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {

    private String grantType; // JWT 인증 타입(뭔지는 잘 모르겠음)
    private String accessToken;
    private String refreshToken;

}