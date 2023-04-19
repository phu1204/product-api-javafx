package com.review.controllers;

import com.review.MyListener;
import com.review.models.Client;
import com.review.models.Product;
import com.review.models.ProductDetail;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemListController implements Initializable {

    @FXML
    private AnchorPane ebay_button;
    @FXML
    private AnchorPane lazada_button;
    @FXML
    private AnchorPane sendo_button;
    @FXML
    private AnchorPane tiki_button;
    @FXML
    private ScrollPane product_list_scroll;
    @FXML
    private GridPane product_list_grid;
    @FXML
    private HBox pagination_list;
    public List<Product> productList = new ArrayList<>();
    public ProductDetail productDetail ;
    private MyListener myListener;
    private Pane pane;
    private int stepPagination = 0;
    private int pageNumDefault = 5;
    private PrimaryController primaryController;
    @FXML
    void ebay_button_press(MouseEvent event) throws IOException, ClassNotFoundException {
        this.primaryController.setCheck("shopee");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
        fxmlLoader.load();
        primaryController.getRatingAggregatorController().setPane(null);
        this.primaryController.setItemListController(fxmlLoader.getController()) ;
        Pattern special = Pattern.compile ("[!'/\"@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(primaryController.getSearch_product().getText());
        if(hasSpecial.find()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Không được nhập ký tự đặc biệt!");
            alert.showAndWait();
            return;
        }
        if(primaryController.getSearch_product().getText() == "" || primaryController.getSearch_product().getText() ==null || primaryController.getSearch_product().getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Nhập ký tự để tìm kiếm sản phẩm!");
            alert.showAndWait();
            return;
        }
        if(primaryController.getShopee().isEmpty()) {
            this.primaryController.getClient().SearchProductShopee(this.primaryController.getSearch_product().getText());
            this.primaryController.getItemListController().productList = this.primaryController.getClient().ReceiveList();
            if(this.primaryController.getItemListController().productList.size() == 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning alert");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy sản phẩm trong Shopee!");
                alert.showAndWait();
                return;
            }
            this.primaryController.setShopee(this.primaryController.getItemListController().productList);
        }
        else {
            this.primaryController.getItemListController().productList = this.primaryController.getShopee();
        }
        this.primaryController.swapItemList();
        this.lazada_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.ebay_button.getStyleClass().add("action");
    }

    @FXML
    void lazada_button_press(MouseEvent event) throws IOException, ClassNotFoundException {
        this.primaryController.setCheck("lazada");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
        fxmlLoader.load();
        primaryController.getRatingAggregatorController().setPane(null);
        this.primaryController.setItemListController(fxmlLoader.getController()) ;
        Pattern special = Pattern.compile ("[!'/\"@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(primaryController.getSearch_product().getText());
        if(hasSpecial.find()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Không được nhập ký tự đặc biệt!");
            alert.showAndWait();
            return;
        }
        if(primaryController.getSearch_product().getText() == "" || primaryController.getSearch_product().getText() ==null || primaryController.getSearch_product().getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Nhập ký tự để tìm kiếm sản phẩm!");
            alert.showAndWait();
            return;
        }
        if(primaryController.getLazada().isEmpty()) {

            this.primaryController.getClient().SearchProductLazada(this.primaryController.getSearch_product().getText());
            this.primaryController.getItemListController().productList = this.primaryController.getClient().ReceiveList();
            if(this.primaryController.getItemListController().productList.size() == 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning alert");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy sản phẩm trong Lazada!");
                alert.showAndWait();
                return;
            }
            this.primaryController.setLazada(this.primaryController.getItemListController().productList);
        }
        else {
            this.primaryController.getItemListController().productList = this.primaryController.getLazada();
        }
        this.primaryController.swapItemList();
        this.ebay_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().add("action");
    }

    @FXML
    void sendo_button_press(MouseEvent event) throws IOException, ClassNotFoundException {

            this.primaryController.setCheck("sendo");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
            fxmlLoader.load();
            primaryController.getRatingAggregatorController().setPane(null);
            this.primaryController.setItemListController(fxmlLoader.getController());
            Pattern special = Pattern.compile ("[!'/\"@#$%&*()_+=|<>?{}\\[\\]~-]");
            Matcher hasSpecial = special.matcher(primaryController.getSearch_product().getText());
            if(hasSpecial.find()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning alert");
                alert.setHeaderText(null);
                alert.setContentText("Không được nhập ký tự đặc biệt!");
                alert.showAndWait();
                return;
            }
        if(primaryController.getSearch_product().getText() == "" || primaryController.getSearch_product().getText() ==null || primaryController.getSearch_product().getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Nhập ký tự để tìm kiếm sản phẩm!");
            alert.showAndWait();
            return;
        }
            if(primaryController.getSendo().isEmpty()) {

                this.primaryController.getClient().SearchProductSendo(this.primaryController.getSearch_product().getText());
                this.primaryController.getItemListController().productList = this.primaryController.getClient().ReceiveList();
                if(this.primaryController.getItemListController().productList.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy sản phẩm trong Sendo!");
                    alert.showAndWait();
                    return;
                }
                this.primaryController.setSendo(this.primaryController.getItemListController().productList);
            }
            else {
                this.primaryController.getItemListController().productList = this.primaryController.getSendo();
            }
            this.primaryController.swapItemList();
        this.ebay_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().add("action");
    }

    @FXML
    void tiki_button_press(MouseEvent event) throws IOException, ClassNotFoundException {
            this.primaryController.setCheck("tiki");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
            fxmlLoader.load();
            primaryController.getRatingAggregatorController().setPane(null);
            this.primaryController.setItemListController(fxmlLoader.getController());
            Pattern special = Pattern.compile ("[!'/\"@#$%&*()_+=|<>?{}\\[\\]~-]");
            Matcher hasSpecial = special.matcher(primaryController.getSearch_product().getText());
            if(hasSpecial.find()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning alert");
                alert.setHeaderText(null);
                alert.setContentText("Không được nhập ký tự đặc biệt!");
                alert.showAndWait();
                return;
            }
        if(primaryController.getSearch_product().getText() == "" || primaryController.getSearch_product().getText() ==null || primaryController.getSearch_product().getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText(null);
            alert.setContentText("Nhập ký tự để tìm kiếm sản phẩm!");
            alert.showAndWait();
            return;
        }
            if(primaryController.getTiki().isEmpty()) {

                this.primaryController.getClient().SearchProduct(this.primaryController.getSearch_product().getText());
                this.primaryController.getItemListController().productList = this.primaryController.getClient().ReceiveList();
                if(this.primaryController.getItemListController().productList.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy sản phẩm trong tiki!");
                    alert.showAndWait();
                    return;
                }
                this.primaryController.setTiki(this.primaryController.getItemListController().productList);
                this.primaryController.swapItemList();
            }
            else
            {
                this.primaryController.getItemListController().productList = this.primaryController.getTiki();
            }
            this.primaryController.swapItemList();
        this.ebay_button.getStyleClass().remove("action");
        this.lazada_button.getStyleClass().remove("action");
        this.sendo_button.getStyleClass().remove("action");
        this.tiki_button.getStyleClass().add("action");
    }
    @FXML
    void pagination_left_press(MouseEvent event) {
        if(this.stepPagination != 0){
            setStepPagination(this.stepPagination-1);
            setPagination();
        }
    }

    @FXML
    void pagination_right_press(MouseEvent event) {
        setStepPagination(this.stepPagination+1);
        setPagination();
    }
    public void setStepPagination(int stepPagination) {
        this.stepPagination = stepPagination;
    }
    private EventHandler clickPagination = new EventHandler() {

        @Override
        public void handle(Event event) {
            Node node;
            for(int i = 0; i < pageNumDefault; i++){
                node = pagination_list.getChildren().get(i);
                node.getStyleClass().remove("button-pagination-action");
            }
            String t = ((Button)event.getSource()).getText();
            ((Button)event.getSource()).getStyleClass().add("button-pagination-action");
        }
    };

    public void setPagination(){
        pagination_list.getChildren().clear();
        Button button;
        int startPage = (pageNumDefault * stepPagination) + 1;
        for(int i = 0; i < pageNumDefault; i++){
            button = new Button();
            button.getStyleClass().add("button-pagination");
            button.addEventHandler(MouseEvent.MOUSE_PRESSED, clickPagination);
            button.setText(String.valueOf(startPage++));
            pagination_list.getChildren().add(button);
        }
    }
    public void openItemList(PrimaryController primaryController1){
        if(pane != null){
            primaryController.setContainer(pane);
        }
        else{
            this.primaryController = primaryController1;
            //productList.addAll(getData());

            if(productList.size()>0){
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(Product product,ProductDetail productDetail) {
                        try {
                            if(primaryController.getCheck().equals("tiki"))
                            {
                                primaryController.getClient().GetDetailProduct(product.getproductID());
                            }
                            else if (primaryController.getCheck().equals("sendo"))
                            {
                                primaryController.getClient().GetDetailProductSendo(product.getPart());
                            }
                            else if (primaryController.getCheck().equals("lazada"))
                            {
                                primaryController.getClient().GetDetailProductLazada(product.getproductID());
                            }
                            else if (primaryController.getCheck().equals("shopee"))
                            {
                                primaryController.getClient().GetDetailProductShopee(product.getproductID(),product.getIdshop());
                            }
                            productDetail = primaryController.getClient().ReceiveProductDetail();
                            primaryController.swapItemDetail(product,productDetail);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
            }

            int col = 0;
            int row = 1;
            try {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/review/item_list.fxml"));
                pane = fxmlLoader.load();
                ItemListController itemListController = fxmlLoader.getController();
                itemListController.primaryController = primaryController1;
                primaryController.setContainer(pane);

                /*--------------------View Product List-------------*/
                for (int i = 0; i < productList.size(); i++){
                    fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/review/item.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(productList.get(i),productDetail, myListener);

                    if(col == 5){
                        col = 0;
                        row++;
                    }

                    itemListController.product_list_grid.add(anchorPane, col++, row);
                    GridPane.setMargin(anchorPane, new Insets(10));
                    itemListController.product_list_scroll.setPadding(new Insets(0, 0, 0, 30));

                }

                /*---------------Set Pagination-------------------*/
                itemListController.pagination_list.getChildren().clear();
                Button button;
                int startPage = (pageNumDefault * stepPagination) + 1;
                for(int i = 0; i < pageNumDefault; i++){
                    button = new Button();
                    button.getStyleClass().add("button-pagination");
                    button.addEventHandler(MouseEvent.MOUSE_PRESSED, event ->{
                        Node node;
                        for(int j = 0; j < pageNumDefault; j++){
                            node = itemListController.pagination_list.getChildren().get(j);
                            node.getStyleClass().remove("button-pagination-action");
                        }
                        ((Button)event.getSource()).getStyleClass().add("button-pagination-action");
                    });
                    button.setText(String.valueOf(startPage++));
                    itemListController.pagination_list.getChildren().add(button);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
