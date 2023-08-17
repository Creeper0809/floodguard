package com.javachip.floodguard.api.satisfaction;

public class SatisfactionResponseDTO {

    private Long id;
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

    public void setRating(double rating) {
        this.rating = rating;
    }
}
