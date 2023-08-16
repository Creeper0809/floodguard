package com.javachip.floodguard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // DB에 직접 생성
@Builder
@ToString // toString
@Getter
@Setter
@NoArgsConstructor // 기본자 생성 ex) new user
@AllArgsConstructor // 생성자 생성
public class Pin {

    @Id //대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db에서 자동으로 번호 생성
    private Long id;

    @Column
    private String url;
    private String coordx;
    private String coordy;
    private String comment;
    private String pos;
    private String alertpos;
    private int type;
    private String userid;
}
