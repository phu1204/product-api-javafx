package com.review.models;

import java.io.Serializable;
import java.util.List;

public class ProductDetail implements Serializable {
    private List<String> imagesUrl;

    private String description;

    private Float rating_average;
    private Integer review_count;
    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getDescription() {
        return description;
    }
    public List<String> getImagesUrl() {
        return imagesUrl;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating_average() {
        return rating_average;
    }

    public void setRating_average(Float rating_average) {
        this.rating_average = rating_average;
    }

    public Integer getReview_count() {
        return review_count;
    }

    public void setReview_count(Integer review_count) {
        this.review_count = review_count;
    }
}
