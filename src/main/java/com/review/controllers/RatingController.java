package com.review.controllers;

import com.review.models.Rate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.IOException;

public class RatingController {
    @FXML
    private Text comment_label;

    @FXML
    private Label date_label;

    @FXML
    private ImageView image_label;

    @FXML
    private Rating rating_label;

    @FXML
    private ImageView user_image_label;

    @FXML
    private Label user_name_label;
    @FXML
    private HBox image_box;


    private String dirPath = "https://img.upanh.tv/2022/11/29/an-danh.png";
    public void setData(Rate rate){
        Image user_image = new Image(rate.getUserImageUrl());
        if(!rate.getImageUrl().isEmpty())
        {
            for(int i = 0; i < rate.getImageUrl().size(); i++){
                ImageView imageView = new ImageView();
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                Image image = new Image(rate.getImageUrl().get(i));
                imageView.setImage(image);
                image_box.getChildren().add(imageView);

                imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/review/image.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ImageController imageController = fxmlLoader.getController();
                    imageController.image_view.setImage(image);
                    root.getStylesheets().add("stylesheets.css");
                    stage.setTitle("Image");
                    stage.setScene(new Scene(root));
                    stage.show();
                });
            }
            //Image image = new Image(rate.getImageUrl().get(0));
            //image_label.setImage(image);
        }
        user_name_label.setText(rate.getUsername());
        user_image_label.setImage(user_image);
        comment_label.setText(rate.getComment());
//        date_label.setText(rate.getDate());
        if(rate.getRating() != null){
            rating_label.setRating(rate.getRating());
        }
    }

}
