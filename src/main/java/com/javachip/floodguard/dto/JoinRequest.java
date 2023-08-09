package com.javachip.floodguard.dto;

import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class JoinRequest {

    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String userid;

    @NotBlank(message = "유저이름이 비어있습니다.")
    private String username;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    public User toEntity() {

        return User.builder()
                .userid(this.userid)
                .password(this.password)
                .username(this.username)
                .role(UserRole.USER)
                .build();
    }
    public User toEntity(String encodedPassword) {

        return User.builder()
                .userid(this.userid)
                .password(encodedPassword)
                .username(this.username)
                .role(UserRole.ADMIN)
                .build();
    }
}