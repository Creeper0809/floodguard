package com.javachip.floodguard.api.survey;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Option> options;

}
