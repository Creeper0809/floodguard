package com.javachip.floodguard.api.satisfaction;

import jakarta.persistence.*;

@Entity
@Table(name = "satisfaction")
public class Satisfaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double rating;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating){
        this.rating = rating;
    }
}
