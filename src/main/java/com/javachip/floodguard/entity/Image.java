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
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    @Builder
    public void ImageData(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }

}
