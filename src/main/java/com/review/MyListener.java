package com.review;

import com.review.models.Product;
import com.review.models.ProductDetail;
import javafx.scene.image.Image;

public interface MyListener {
    public void onClickListener(Product product, ProductDetail productDetail);
}
