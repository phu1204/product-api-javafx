package com.review.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoDetailController implements Initializable {
    @FXML
    public Label Info_detail;

    @FXML
    public WebView description_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        description_view.getEngine().loadContent("<html>hello, world</html>", "text/html");
    }
}
