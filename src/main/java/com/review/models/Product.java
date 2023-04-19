package com.review.models;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private List<Rate> rates;
    private String productID;
    private String productName;
    private long price;
    private long price_sale;
    private String imageUrl;
    private String part;
    private String idshop;
    private Float rating_average;

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getIdshop() {
        return idshop;
    }

    public void setIdshop(String idshop) {
        this.idshop = idshop;
    }

    public String getproductID() {
        return productID;
    }

    public void setproductID(String productID) {
        this.productID = productID;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(long price_sale) {
        this.price_sale = price_sale;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Float getRating_average() {
        return rating_average;
    }

    public void setRating_average(Float rating_average) {
        this.rating_average = rating_average;
    }
}
