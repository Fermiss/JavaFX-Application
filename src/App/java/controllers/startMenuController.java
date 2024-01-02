package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class startMenuController {
    @FXML
    private Button MongodbButton;

    @FXML
    private Button MysqlButton;

    @FXML
    void initialize() {

        MongodbButton.setOnAction(event->{
            launch("MongoDB");
        });
        MysqlButton.setOnAction(event -> {
            launch("MySQL");
        });
    }

    void launch(String dataBase){
        FXMLLoader loader = new FXMLLoader();
        MysqlButton.getScene().getWindow().hide();
        loader.setLocation(getClass().getResource("/auth.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        authController controller = loader.getController();
        controller.setCurrDB(dataBase);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}