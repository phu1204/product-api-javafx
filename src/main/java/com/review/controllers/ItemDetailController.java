package com.review.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemDetailController implements Initializable{
    @FXML
    public Text product_name_label;
    public InfoDetailController infoDetailController;
    public RatingListController ratingListController;
    @FXML
    public Label product_price_label;

    @FXML
    public ImageView product_image_1;

    @FXML
    public ImageView product_image_2;

    @FXML
    public ImageView product_image_3;
    @FXML
    public Label product_sale_price_label;
    @FXML
    private AnchorPane info_button;

    @FXML
    private AnchorPane rating_button;

    @FXML
    public AnchorPane rating_list;
    private static PrimaryController primaryController;
    public Pane rating_pane;
    public Pane info_pane;
    @FXML
    void info_button_press(MouseEvent event) throws IOException{
//        Pane newPane = FXMLLoader.load(getClass().getResource("/com/review/info_detail.fxml"));

        this.rating_list.getChildren().clear();
        this.rating_list.getChildren().addAll(primaryController.getItemDetailController().info_pane);
        this.info_button.getStyleClass().remove("action");
        this.rating_button.getStyleClass().remove("action");
        this.info_button.getStyleClass().add("action");

    }


    @FXML
    void rating_button_press(MouseEvent event) throws IOException{
        if(rating_pane != null){
            this.rating_list.getChildren().clear();
            this.rating_list.getChildren().addAll(primaryController.getItemDetailController().rating_pane);
            this.info_button.getStyleClass().remove("action");
            this.rating_button.getStyleClass().remove("action");
            this.rating_button.getStyleClass().add("action");
        }
        else{
            this.rating_pane = FXMLLoader.load(getClass().getResource("/com/review/rating_list.fxml"));
            this.rating_list.getChildren().clear();
            this.rating_list.getChildren().addAll(primaryController.getItemDetailController().rating_pane);
            this.info_button.getStyleClass().remove("action");
            this.rating_button.getStyleClass().add("action");
        }

    }
    @FXML
    void returnItemList(MouseEvent event) throws IOException, ClassNotFoundException {
        primaryController.swapItemList();
    }
    public void openItemDetail(PrimaryController primaryController1){
        primaryController = primaryController1;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/item_detail.fxml"));
            Pane newPane = fxmlLoader.load();
            ItemDetailController itemDetailController = fxmlLoader.getController();
            primaryController.setContainer(newPane);
            primaryController.setItemDetailController(itemDetailController);
            newPane = FXMLLoader.load(getClass().getResource("/com/review/info_detail.fxml"));
            itemDetailController.rating_list.getChildren().addAll(newPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
