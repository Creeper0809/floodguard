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

    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    @NotBlank(message = "유저이름이 비어있습니다.")
    private String username;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "휴대폰번호가 비어있습니다.")
    private String phonenumber;

    public User toEntity(String encodedPassword) {

        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .username(this.username)
                .phonenumber(this.phonenumber)
                .role(UserRole.ADMIN)
                .build();
    }
}
