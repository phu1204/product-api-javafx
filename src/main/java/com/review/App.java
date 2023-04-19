package com.review;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.review.controllers.PrimaryController;
import com.review.models.Client;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/review/primary.fxml"));
        Parent root = fxmlLoader.load();
        PrimaryController primaryController = fxmlLoader.getController();
        root.getStylesheets().add("stylesheets.css");
        stage.setTitle("Product Review App");
        stage.setScene(new Scene(root));

        //Bắt sự kiện close
        stage.setOnHidden(e -> {
            try {
                primaryController.disconnect();
                Platform.exit();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args){
        launch();
    }

}