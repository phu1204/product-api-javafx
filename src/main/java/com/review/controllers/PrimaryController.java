package com.review.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.review.models.*;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.text.DecimalFormat;

public class PrimaryController implements Initializable {
    private Client client ;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    private String check;

    public List<Product> getTiki() {
        return Tiki;
    }

    public void setTiki(List<Product> tiki) {
        Tiki = tiki;
    }

    private List<Product> Tiki = new ArrayList<>();

    public List<Product> getShopee() {
        return Shopee;
    }

    public void setShopee(List<Product> shopee) {
        Shopee = shopee;
    }

    private List<Product> Shopee = new ArrayList<>();

    public List<Product> getSendo() {
        return Sendo;
    }

    public void setSendo(List<Product> sendo) {
        Sendo = sendo;
    }

    private List<Product> Sendo = new ArrayList<>() ;

    public List<Product> getLazada() {
        return Lazada;
    }

    public void setLazada(List<Product> lazada) {
        Lazada = lazada;
    }

    private List<Product> Lazada = new ArrayList<>();

    @FXML
    private BorderPane container;

    public ItemListController getItemListController() {
        return itemListController;
    }

    public void setItemListController(ItemListController itemListController) {
        this.itemListController = itemListController;
    }

    private ItemListController itemListController;
    private ItemDetailController itemDetailController;
    private InfoDetailController infoDetailController;

    public RatingAggregatorController getRatingAggregatorController() {
        return ratingAggregatorController;
    }

    public void setRatingAggregatorController(RatingAggregatorController ratingAggregatorController) {
        this.ratingAggregatorController = ratingAggregatorController;
    }

    private RatingAggregatorController ratingAggregatorController;

    @FXML
    private Label rating_aggregator_button;

    @FXML
    private Label search_product_button;

    public TextField getSearch_product() {
        return search_product;
    }

    @FXML
    private TextField search_product ;
    private int page=1;

    public void setInfoDetailController(InfoDetailController infoDetailController) {
        this.infoDetailController = infoDetailController;
    }

    public InfoDetailController getInfoDetailController() {
        return infoDetailController;
    }
    public ItemDetailController getItemDetailController() {
        return itemDetailController;
    }

    public void setItemDetailController(ItemDetailController itemDetailController) {
        this.itemDetailController = itemDetailController;
    }

    @FXML
    void search_enter(ActionEvent event)throws IOException,ClassNotFoundException {
        //Kiểm tra không nhap chữ
        if(search_product.getText() == "" || search_product.getText() ==null || search_product.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Nhập ký tự để tìm kiếm sản phẩm!");
            alert.showAndWait();
            return;
        }
        //Kiểm tra kí tự đặc biệt
        Pattern special = Pattern.compile ("[!'/\"@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(search_product.getText());
        if(hasSpecial.find()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Không được nhập ký tự đặc biệt!");
            alert.showAndWait();
            return;
        }

        this.rating_aggregator_button.getStyleClass().remove("action");
        this.search_product_button.getStyleClass().remove("action");
        client.SearchProduct(search_product.getText().trim());
        setCheck("tiki");
        Tiki = new ArrayList<>();
        Sendo = new ArrayList<>();
        Shopee = new ArrayList<>();
        Lazada = new ArrayList<>();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
        fxmlLoader.load();
        itemListController = fxmlLoader.getController();
        itemListController.productList = client.ReceiveList();

        ///Kiểm tra không có sản phẩm
        if(itemListController.productList.size() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy sản phẩm trong tiki!");
            alert.showAndWait();
            return;
        }
        ratingAggregatorController.setPane(null);
        ratingAggregatorController.check = "tiki";
        swapItemList();
        this.search_product_button.getStyleClass().add("action");
    }

    @FXML
    void rating_aggregator_press(MouseEvent event) throws IOException, ClassNotFoundException, InterruptedException {

        this.rating_aggregator_button.getStyleClass().remove("action");
        this.search_product_button.getStyleClass().remove("action");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
        fxmlLoader.load();
        //Get rating default tiki
        if(check.equals("tiki")) {
            client.GetReviewProduct(this.getItemListController().productList.get(0).getproductID(), 1);
            ratingAggregatorController.rateList = client.ReceiveListReviews();
            ratingAggregatorController.Tiki = ratingAggregatorController.rateList;
        }
        if(check.equals("sendo")) {
            client.GetReviewProductSendo(this.getItemListController().productList.get(0).getproductID(), 1);
            ratingAggregatorController.rateList = client.ReceiveListReviews();
            ratingAggregatorController.Sendo = ratingAggregatorController.rateList;
        }
        if(check.equals("shopee")) {
            client.GetReviewProductShopee(this.getItemListController().productList.get(0).getproductID(), this.getItemListController().productList.get(0).getIdshop(), 1);
            ratingAggregatorController.rateList = client.ReceiveListReviews();
            ratingAggregatorController.Shoppe = ratingAggregatorController.rateList;
        }
        if(check.equals("lazada")) {
            client.GetReviewProductLazada(this.getItemListController().productList.get(0).getproductID(), 1);
            ratingAggregatorController.rateList = client.ReceiveListReviews();
            ratingAggregatorController.Lazada = ratingAggregatorController.rateList;
        }
        Product product = this.getItemListController().productList.get(0);
        swapRatingAggregator(product);
        this.rating_aggregator_button.getStyleClass().add("action");
    }

    @FXML
    void search_product_press(MouseEvent event) {
        this.rating_aggregator_button.getStyleClass().remove("action");
        this.search_product_button.getStyleClass().remove("action");

        swapItemList();
        this.search_product_button.getStyleClass().add("action");

    }

    public void setContainer(Pane newPane){
        container.setCenter(newPane);
    }

    public void swapItemList(){
        itemListController.openItemList(this);
    }
    public void swapRatingAggregator(Product product){
        try {
            ratingAggregatorController.openRatingAggregator(this,product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void swapItemDetail(Product product, ProductDetail productDetail){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/item_detail.fxml"));
            fxmlLoader.load();
            String patternTienTe = "###,###,###đ";
            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe);
            itemDetailController = fxmlLoader.getController();
            itemDetailController.openItemDetail(this);
            itemDetailController.product_name_label.setText(product.getProductName());
            itemDetailController.product_price_label.setText(formatTienTe.format(product.getPrice_sale()));
            itemDetailController.product_sale_price_label.setText(formatTienTe.format(product.getPrice()));
            List<String> images=new ArrayList<>();
            images = productDetail.getImagesUrl();
            String deteleWebp1 = product.getImageUrl().replace(".webp","");
                if(images.size()>=3) {
                    String deteleWebp2 = images.get(1).replace(".webp","");
                    String deteleWebp3 = images.get(2).replace(".webp","");
                        Image image = new Image(deteleWebp1);
                        Image image2 = new Image(deteleWebp2);
                        Image image3 = new Image(deteleWebp3);
                        itemDetailController.product_image_1.setImage(image);
                        itemDetailController.product_image_2.setImage(image2);
                        itemDetailController.product_image_3.setImage(image3);
                }
                else {
                    Image image = new Image(deteleWebp1);
                    itemDetailController.product_image_1.setImage(image);
                }
            //info_detail
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/info_detail.fxml"));
            Pane newpane = fxmlLoader.load();
            itemDetailController.infoDetailController = fxmlLoader.getController();
            itemDetailController.infoDetailController.description_view.getEngine().loadContent(productDetail.getDescription(),"text/html");
            itemDetailController.rating_list.getChildren().addAll(newpane);
            itemDetailController.info_pane = newpane;
            //rating_list_detail
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/rating_list.fxml"));

            Pane newpane1 = fxmlLoader.load();
            itemDetailController.ratingListController = fxmlLoader.getController();
            if(check.equals("tiki")) {
                client.GetReviewProduct(product.getproductID(),1);
            }
            if(check.equals("sendo")){
                client.GetReviewProductSendo(product.getproductID(), 1);
            }
            if(check.equals("shopee")){
                client.GetReviewProductShopee(product.getproductID(),product.getIdshop(),1);
            }
            if(check.equals("lazada")){
                client.GetReviewProductLazada(product.getproductID(), 1);
            }
            itemDetailController.ratingListController.rateList = client.ReceiveListReviews();
            itemDetailController.ratingListController.openRatingList(this,page);
            DecimalFormat formatrating= new DecimalFormat("###.###");
            itemDetailController.ratingListController.rating_detail_product.setText(formatrating.format(productDetail.getRating_average()));
            itemDetailController.ratingListController.count_rating_product.setText(String.valueOf(productDetail.getReview_count())+" Đánh giá");

            itemDetailController.rating_pane = newpane1;
        }catch (IOException | ClassNotFoundException e){

        }
    }
    public void disconnect() throws IOException, ClassNotFoundException {
        client.disconnect();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                client=new Client();
                client.ConnectClient();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
                fxmlLoader.load();
                itemListController = fxmlLoader.getController();

                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/rating_aggregator.fxml"));
                fxmlLoader.load();
                ratingAggregatorController = fxmlLoader.getController();

                client.SearchProduct("iphone");
                itemListController.productList = client.ReceiveList();
                Tiki = itemListController.productList;
                search_product.setText("iphone");
                setCheck("tiki");
                swapItemList();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
    }
    public void SetClient(Client cl){
        this.client=client;
    }
    public Client getClient(){
        return client;
    }

}
