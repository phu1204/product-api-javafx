module com.review.reviewapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires org.json;
    requires org.jsoup;

    opens com.review to javafx.fxml;
    exports com.review;
    exports com.review.controllers;
    opens com.review.controllers to javafx.fxml;
    exports com.review.models;
    opens com.review.models to javafx.fxml;
}