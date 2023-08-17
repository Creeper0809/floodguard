package com.javachip.floodguard.dto;

import lombok.Data;

import java.util.List;

@Data
public class SurveyDTO {
    private Long id;
    private String title;
    private List<QuestionDTO> questions;

}
