package controllers;

import java.io.IOException;
import java.util.List;

import basehandler.DataBaseHandler;
import basehandler.MongodbBaseHandler;
import datalayer.AccountRepository;
import datalayer.CustomerRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mongodbCollections.TypeCustomer;
import usersClassesMySQL.Account;
import usersClassesMySQL.Customer;

public class registrationController {
    private String selectedDB;

    public void setCurrDB(String currDB){ selectedDB = currDB; }
    @FXML
    private ChoiceBox<String> customerTypeChoiceBox;

    @FXML
    private TextField loginField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberPhoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button sighUpButton;

    @FXML
    void initialize() {
        String[] types0fCustomers = {"Физическое лицо","Юр. лицо","Иное"};
        customerTypeChoiceBox.getItems().addAll(types0fCustomers);

        sighUpButton.setOnAction(actionEvent -> {
            String login = loginField.getText().trim();
            String pass = passwordField.getText().trim();
            String fullName = nameField.getText().trim();
            String mail = mailField.getText().trim();
            String number = numberPhoneField.getText().trim();
            String typeOfCustomer = customerTypeChoiceBox.getValue();
            if (!(login.equals("") || pass.equals("") || fullName.equals("") && mail.equals("") || number.equals("") || typeOfCustomer.equals(""))) {
                switch (selectedDB) {
                    case "MySQL" -> MySQLMode(login, pass, fullName, number, mail, typeOfCustomer);
                    case "MongoDB" -> MongoDBMode(login, pass, fullName, number, mail, new TypeCustomer(typeOfCustomer, 0));
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Пожалуйста введите незаполеннные поля.");
                alert.setTitle("Внимание");
                alert.showAndWait();
            }
        });
    }
    public void launchAuth(){
        sighUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/auth.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        authController controller = loader.getController();
        controller.setCurrDB(selectedDB);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void MongoDBMode(String login, String pass, String fullName, String number, String mail, TypeCustomer typeOfCustomer){
        AccountRepository accountRepository = new AccountRepository();
        List<mongodbCollections.Account> accounts = accountRepository.getAll();
        if (accounts.stream().filter(x-> x.getLogin().equals(login)).count() == 1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Данный логин уже занят, укажите другой.");
            alert.setTitle("Внимание");
            alert.showAndWait();
        }else {
            mongodbCollections.Account account = new mongodbCollections.Account(login, pass, "client");
            CustomerRepository customerRepository = new CustomerRepository();
            accountRepository.add(account);
            customerRepository.add(new mongodbCollections.Customer(fullName, mail, number, typeOfCustomer, account));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Уведомление");
            alert.setContentText("Регистрация прошла успешно.");
            alert.showAndWait();
            launchAuth();
        }
    }
    public void MySQLMode(String login, String pass, String fullName, String number, String mail, String typeOfCustomer){
        DataBaseHandler handler = new DataBaseHandler();
        if (handler.containsLogin(login)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Данный логин уже занят, укажите другой.");
            alert.setTitle("Внимание");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Уведомление");
            alert.setContentText("Регистрация прошла успешно.");
            alert.showAndWait();
            Account newAccount = new Account(login, pass, "client");
            handler.createNewAccount(newAccount);
            Customer newCustomer = new Customer(fullName,number, mail, handler.getIdAccount(login),handler.getCustomerTypeId(typeOfCustomer));
            handler.createNewCustomer(newCustomer);
            launchAuth();
        }
    }
}

