package controllers;


import java.io.IOException;
import java.util.List;

import basehandler.DataBaseHandler;
import basehandler.MongodbBaseHandler;
import controllers.MongoDB.clientMenuControllerMongo;
import controllers.MySQL.*;
import datalayer.AccountRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import usersClassesMySQL.Account;

public class authController {
    AccountRepository accountRepository = new AccountRepository();
    private String selectedDB;

    public void setCurrDB(String currDB){ selectedDB = currDB; }
    @FXML
    private Button authButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrationButton;

    @FXML
    void initialize() {
        registrationButton.setOnAction(actionEvent -> {
            registrationButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/registration.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            registrationController controller = loader.getController();
            controller.setCurrDB(selectedDB);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        authButton.setOnAction(actionEvent -> {
            if (!(loginField.getText().trim().equals("") &&  passwordField.getText().trim().equals(""))){
                switch (selectedDB) {
                    case "MySQL" -> MySQLMode(loginField.getText(), passwordField.getText());
                    case "MongoDB" -> MongoDBMode(loginField.getText(), passwordField.getText());
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setContentText("Вы не ввели логин и/или пароль");
                alert.showAndWait();
            }
        });
    }
    private void MongoDBMode(String login, String password){
        FXMLLoader loader = new FXMLLoader();
        List<mongodbCollections.Account> accountList = accountRepository.getAll();
        mongodbCollections.Account account = new mongodbCollections.Account(login, password);
        if(accountList.stream().filter(x -> x.getLogin().equals(account.getLogin())).anyMatch(x -> x.getPassword().equals(account.getPassword()))){
            account.setRole(accountList.stream().filter(x -> x.getLogin().equals(account.getLogin())).findFirst().get().getRole());
            switch (account.getRole()){
                case "client":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/clientMenuControllerMongoDB.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    clientMenuControllerMongo controller = loader.getController();
                    controller.setCurrLogin(account.getLogin());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
                case "admin":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/adminMenuMongo.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    root = loader.getRoot();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
                case "manager":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/managerMenu.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    root = loader.getRoot();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
                case "dispatcher":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/dispatcherMenu.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    root = loader.getRoot();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
                case "director":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/directorMenuMongo.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    root = loader.getRoot();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
            }
        }
    }
    private void MySQLMode(String login, String password){
        loginUser(login, password);
    }
    private void loginUser(String login, String password){
        FXMLLoader loader = new FXMLLoader();
        DataBaseHandler dbHandler = new DataBaseHandler();
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        String role = dbHandler.getRole(account);
        if (role != null){
            switch (role){
                case "client":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/clientMenu.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    controllers.MySQL.clientMenuController controller = loader.getController();
                    controller.setCurrLogin(account.getLogin());
                    controller.setCurrDB(selectedDB);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
                case "admin":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/adminMenu.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    root = loader.getRoot();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
                case "manager":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/managerMenu.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    root = loader.getRoot();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
                case "dispatcher":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/dispatcherMenu.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    root = loader.getRoot();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
                case "director":
                    registrationButton.getScene().getWindow().hide();
                    loader.setLocation(getClass().getResource("/directorMenu.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    root = loader.getRoot();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    break;
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Попробуйте ещё раз");
            alert.setContentText("Неверный логин и/или пароль.");
            alert.showAndWait();
        }
    }
}

