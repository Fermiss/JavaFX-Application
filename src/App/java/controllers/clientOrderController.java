package controllers;

import controllers.MongoDB.*;
import controllers.MySQL.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import basehandler.DataBaseHandler;
import datalayer.BrandAutoRepository;
import datalayer.CustomerRepository;
import datalayer.OrderRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mongodbCollections.BrandAuto;
import org.bson.types.ObjectId;
import usersClassesMySQL.Order;

public class clientOrderController {
    BrandAutoRepository brandAutoRepository = new BrandAutoRepository();

    CustomerRepository customerRepository = new CustomerRepository();
    OrderRepository orderRepository = new OrderRepository();

    private String selectedDB;

    public void setCurrDB(String currDB){ selectedDB = currDB; }
    private String currLogin;

    public void setCurrLogin(String login){
        currLogin = login;
    }

    DataBaseHandler handler = new DataBaseHandler();

    @FXML
    private ChoiceBox<String> brandAutoChoiceBox;

    @FXML
    private ChoiceBox<String> modelAutoChoiceBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private Button submitOrderButton;

    @FXML
    void markChocied(MouseEvent event) {
        switch (selectedDB) {
            case "MySQL" -> {
                modelAutoChoiceBox.getItems().clear();
                modelAutoChoiceBox.getItems().addAll(handler.getAllModelsByBrand(brandAutoChoiceBox.getValue()));
            }
            case "MongoDB" -> {
                modelAutoChoiceBox.getItems().clear();
                List<BrandAuto> brandAutos = brandAutoRepository.getAll().stream().filter(x -> x.getName().equals(brandAutoChoiceBox.getValue())).toList();
                modelAutoChoiceBox.getItems().addAll(brandAutos.get(0).getModels());
            }
        }
    }

    @FXML
    void initialize() {
        submitOrderButton.setOnAction(event -> {
            switch (selectedDB) {
                case "MySQL" -> {
                    brandAutoChoiceBox.getItems().addAll(handler.getAllBrands());
                    if (modelAutoChoiceBox.getValue() == null | startDatePicker.getValue() == null | brandAutoChoiceBox.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Внимание");
                        alert.setContentText("Не были введены необходимые данные.");
                        alert.showAndWait();
                    } else {
                        LocalDate date = startDatePicker.getValue();
                        String strDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        Date date2 = Date.valueOf(strDate);
                        Order order = new Order(date2,
                                handler.getIdCustomerByLogin(currLogin),
                                handler.getIdModelAuto(modelAutoChoiceBox.getValue()));
                        handler.createOrder(order);
                        submitOrderButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/clientMenu.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        clientMenuController controller = loader.getController();
                        controller.setCurrLogin(currLogin);
                        controller.setCurrDB(selectedDB);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                }
                case "MongoDB" -> {
                    List<String> marks = new ArrayList<>();
                    brandAutoRepository.getAll().forEach(x -> marks.add(x.getName()));
                    brandAutoChoiceBox.getItems().addAll(marks);
                    if (modelAutoChoiceBox.getValue() == null | startDatePicker.getValue() == null | brandAutoChoiceBox.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Внимание");
                        alert.setContentText("Не были введены необходимые данные.");
                        alert.showAndWait();
                    } else {
                        ObjectId customerId = customerRepository.getAll().stream().filter(x->x.getAccount().getLogin().equals(currLogin)).limit(1).toList().get(0).getId();
                        LocalDate date = startDatePicker.getValue();
                        String strDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        Date date2 = Date.valueOf(strDate);
                        mongodbCollections.Order order = new mongodbCollections.Order(
                                date2,
                                customerId,
                                customerRepository.getAll().stream().filter(x->x.getId().equals(customerId)).limit(1).toList().get(0).getFullName(),
                                customerRepository.getAll().stream().filter(x->x.getId().equals(customerId)).limit(1).toList().get(0).getPhone(),
                                brandAutoChoiceBox.getValue(),
                                modelAutoChoiceBox.getValue());
                        orderRepository.add(order);
                        submitOrderButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/clientMenuControllerMongoDB.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        clientMenuControllerMongo controller = loader.getController();
                        controller.setCurrLogin(currLogin);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                }
            }
        });
    }

}
