package com.javachip.floodguard.dto;

import com.javachip.floodguard.entity.User;
import com.javachip.floodguard.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class RegisterRequestDTO {

    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordCheck;
    @NotBlank
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
