package com.javachip.floodguard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // DB에 직접 생성
@Builder
@Data
@NoArgsConstructor // 기본자 생성 ex) new user
@AllArgsConstructor // 생성자 생성
public class Favorite {
    @Id //대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db에서 자동으로 번호 생성
    private Long id;

    @Column
    private Long userid;
    private Long pinid;

}
