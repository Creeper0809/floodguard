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
public class RegisterRequestDTO {


    private String email;

    private String username;

    private String password;
    private String passwordCheck;

    private String phonenumber;

    public User toEntity(String encodedPassword) {

        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .username(this.username)
                .phonenumber(this.phonenumber)
                .role(UserRole.USER)
                .build();
    }
}
