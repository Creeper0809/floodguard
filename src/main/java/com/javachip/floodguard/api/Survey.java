package com.javachip.floodguard.api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode
@Entity


public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // 질문지
    private String question1;
    // 불만족 ~ 만족
    private String question1_1;
    private String question1_2;
    private String question1_3;
    private String question1_4;
    private String question1_5;

    private String getQuestion2;
    private String getQuestion2_1;
    private String getQuestion2_2;
    private String getQuestion2_3;
    private String getQuestion2_4;
    private String getQuestion2_5;

    //...

}



