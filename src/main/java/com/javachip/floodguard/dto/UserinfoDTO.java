package com.javachip.floodguard.dto;

import com.javachip.floodguard.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserinfoDTO {
    private long userid;
    private String username;
    private String role;
    public UserinfoDTO(User user){
        this.userid = user.getId();
        this.username = user.getUsername();
    }
}
