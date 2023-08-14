package com.javachip.floodguard.dto;

import com.javachip.floodguard.entity.Favorite;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Component
public class FavoriteDTO {

    private Long userid;
    private Long pinid;

    public Favorite toEntity(Long no,Long userid) {

        return Favorite.builder()
                .userid(userid)
                .pinid(no)
                .build();
    }
}
