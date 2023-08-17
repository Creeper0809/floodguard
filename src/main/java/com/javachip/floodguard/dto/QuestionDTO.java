package com.javachip.floodguard.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String content;
    private List<OptionDTO> options;
}
