package controllers.MySQL;

import basehandler.DataBaseHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import usersClassesMySQL.Account;

import java.io.IOException;
import java.util.Objects;

public class adminMenuController {
    DataBaseHandler handler = new DataBaseHandler();

    String[] roles = {"admin", "manager", "dispatcher", "director"};

    @FXML
    private Button addNewAccountButton;

    @FXML
    private TextField newLogField;

    @FXML
    private TextField newPassField;

    @FXML
    private Button changeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button goToAuthButton;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableColumn<Account, Integer> id_col;

    @FXML
    private TableColumn<Account, String> log_col;

    @FXML
    private TableColumn<Account, String> pass_col;

    @FXML
    private TableColumn<Account, String> role_col;

    @FXML
    private TableView<Account> tableAccounts;

    @FXML
    void initialize() {
        choiceBox.getItems().addAll(roles);
        showAllAccounts();

        changeButton.setOnAction(event -> {
            ObservableList <Account> accounts = tableAccounts.getSelectionModel().getSelectedItems();
            int id = accounts.get(0).getId();
            String newLog = newLogField.getText().trim();
            String newPass = newPassField.getText().trim();
            String newRole = choiceBox.getValue();
            if (newLog.isBlank() && newRole == null && newPass.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Предупреждение");
                alert.setContentText("Пожалуйста заполните пустые поля.");
                alert.showAndWait();
            }else if(accounts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Предупреждение");
                alert.setContentText("Вы не выбрали учётную запись, которую хотите изменить.");
                alert.showAndWait();
            }else if (Objects.equals(accounts.get(0).getRole(), "admin")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Предупреждение");
                alert.setContentText("Вы не можете менять учётную запись администратора.");
                alert.showAndWait();
            }else if(newLog.isBlank() && newPass.isBlank()){
                handler.updateRoleAccount(id, newRole);
                showAllAccounts();
            }
            else {
                handler.updateAccount(id, newLog, newPass , newRole);
                showAllAccounts();
                newLogField.clear();
                newPassField.clear();
            }
        });

        goToAuthButton.setOnAction(event -> {
            goToAuthButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/auth.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        deleteButton.setOnAction(event -> {
            ObservableList <Account> accounts = tableAccounts.getSelectionModel().getSelectedItems();
            if (accounts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Вы не выбрали учётную запись.");
                alert.showAndWait();
            }else if(accounts.get(0).getRole().equals("admin")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Вы не можете удалить администратора.");
                alert.showAndWait();
            }
            else {
                handler.deleteAccount(accounts.get(0).getId());
                showAllAccounts();
            }
        });

        addNewAccountButton.setOnAction(event -> {
            if (newPassField.getText().trim().equals("") &&
                    newLogField.getText().trim().equals("") &&
                    choiceBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Вы не заполнили данные для новой учётной записи.");
                alert.showAndWait();
            }else if(handler.containsLogin(newLogField.getText().trim())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setContentText("Учётная запись с таким логином уже сущействует.");
                alert.showAndWait();
            }
            else {
                handler.createNewAccount(new Account(newLogField.getText().trim(),
                        newPassField.getText().trim(),
                        choiceBox.getValue()));
                newLogField.clear();
                newPassField.clear();
                showAllAccounts();
            }
        });
    }
    private void showAllAccounts(){
        ObservableList<Account> accounts = handler.getAllAccountsForAdmin();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        log_col.setCellValueFactory(new PropertyValueFactory<>("login"));
        pass_col.setCellValueFactory(new PropertyValueFactory<>("password"));
        role_col.setCellValueFactory(new PropertyValueFactory<>("role"));
        tableAccounts.setItems(accounts);
    }
}
