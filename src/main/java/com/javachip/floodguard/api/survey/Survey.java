package com.javachip.floodguard.api.survey;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
}
