package com.review.controllers;

import com.review.MyListener;
import com.review.models.Product;
import com.review.models.ProductDetail;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Rating;

import java.text.DecimalFormat;

public class ItemController {

    @FXML
    private ImageView image_view;

    @FXML
    private Label price_label;

    @FXML
    private Label product_name_label;

    @FXML
    private Rating rating_label;

    @FXML
    private Label sale_price_label;

    private Product product;
    private ProductDetail productDetail;
    private MyListener myListener;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(product,productDetail);
    }
    public void setData(Product product,ProductDetail productDetail, MyListener myListener){
        String patternTienTe = "###,###,###đ";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
        this.product = product;
        this.productDetail = productDetail;
        this.myListener = myListener;
        product_name_label.setText(product.getProductName());
        price_label.setText(formatTienTe.format(product.getPrice()));
        sale_price_label.setText(formatTienTe.format(product.getPrice_sale()));
        Image image = new Image(product.getImageUrl());
        image_view.setImage(image);
        if(product.getRating_average() != null){
            rating_label.setRating(Float.valueOf(product.getRating_average()));
        }
    }
}
