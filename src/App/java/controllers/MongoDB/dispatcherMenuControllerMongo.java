package controllers.MongoDB;

import basehandler.MongodbBaseHandler;
import datalayer.CustomerRepository;
import datalayer.OrderRepository;
import datalayer.TypeServiceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mongodbCollections.*;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class dispatcherMenuControllerMongo {
    CustomerRepository customerRepository = new CustomerRepository();
    TypeServiceRepository typeServiceRepository = new TypeServiceRepository();
    OrderRepository orderRepository = new OrderRepository();

    @FXML
    private TableColumn<Order, String> auto_col;

    @FXML
    private TableColumn<Order, Date> endDate_col;

    @FXML
    private TableColumn<Order, String> nameService_col;

    @FXML
    private TableColumn<Order, Integer> priceOrder_col;

    @FXML
    private TableColumn<Order, Date> startDate_col;

    @FXML
    private TableView<Order> table_orders;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField priceField;

    @FXML
    private Button submitButton;

    @FXML
    private TableColumn<Order, String> nameClient_col;

    @FXML
    private Button addServiceInOrderButton;

    @FXML
    private TableColumn<Service, Integer> priceService_col;

    @FXML
    private TableView<Service> services_table;

    @FXML
    private Button changePriceButton;

    @FXML
    private ChoiceBox<String> categoryServiceChoiceBox;

    @FXML
    private ChoiceBox<String> serviceChoiceBox;

    @FXML
    private Button deleteServiceInOrderButton;

    @FXML
    private Button goToAuthButton;

    @FXML
    void loadServices(MouseEvent event) {
        serviceChoiceBox.getItems().clear();
        List<String> lst = new ArrayList<>();
        typeServiceRepository.getAll().forEach(x->lst.add(x.getName()));
        serviceChoiceBox.getItems().addAll(lst);
    }

    @FXML
    void showOrderServices(MouseEvent event) {
        ObservableList<Order> orders = table_orders.getSelectionModel().getSelectedItems();
        if (orders.size()!=0) {
            showServicesInOrder(orders);
        }
    }

    @FXML
    void initialize() {
        showAllOrders();
        List<String> lst = new ArrayList<>();
        typeServiceRepository.getAll().forEach(x->lst.add(x.getName()));
        categoryServiceChoiceBox.getItems().addAll(lst);

        submitButton.setOnAction(event -> {
            if (datePicker.getValue() != null) {
                ObservableList<Order> orders = table_orders.getSelectionModel().getSelectedItems();
                LocalDate date = datePicker.getValue();
                String strDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Date date2 = Date.valueOf(strDate);
                //handler.updateOrder(orders.get(0).getId(), date2);
                showAllOrders();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Не была введена дата окончания заказа.");
                alert.showAndWait();
            }
        });

        addServiceInOrderButton.setOnAction(event -> {
            if (serviceChoiceBox.getValue() != null && categoryServiceChoiceBox.getValue() != null) {
                ObservableList<Order> orders = table_orders.getSelectionModel().getSelectedItems();
                double sale = customerRepository.getAll().stream().filter(x -> x.getId().equals(orders.get(0).getId_customer())).findFirst().get().getTypeCustomer().getDiscount();
                //handler.addServiceInOrder(orders.get(0).getId(), serviceChoiceBox.getValue());
                //calculatePriceInOrder(orders, handler.getPriceServiceByName(serviceChoiceBox.getValue()), sale);
                showServicesInOrder(orders);
                showAllOrders();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Выберите услугу, которую хотите добавить в заказ.");
                alert.setTitle("Внимание");
                alert.showAndWait();
            }
        });

        changePriceButton.setOnAction(event -> {
            if (!(!priceField.getText().equals("") && !priceField.getText().isBlank())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Предупреждение");
                alert.setContentText("Вы не указали цену.");
                alert.showAndWait();
            } else {
                ObservableList<Order> orders = table_orders.getSelectionModel().getSelectedItems();
                ObservableList<Service> services = services_table.getSelectionModel().getSelectedItems();
                if (orders.size() == 0 && services.size() == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setContentText("Вы не выбрали заказ в котором хотите изменить услугу.");
                    alert.showAndWait();
                } else {
                    int price = Integer.parseInt(priceField.getText().trim());
                  /*  handler.updateServiceInOrder(services.get(0).getName(),
                            services.get(0).getPrice() + price,
                            orders.get(0).getId());
                    showServicesInOrder(orders);
                    calculatePriceInOrder(orders, price, handler.getDiscountByCustomerId(orders.get(0).getCustomer_id()));*/
                    showAllOrders();
                }
            }
        });

        deleteServiceInOrderButton.setOnAction(event -> {
            ObservableList<Service> services = services_table.getSelectionModel().getSelectedItems();
            ObservableList<Order> orders = table_orders.getSelectionModel().getSelectedItems();
            if (services.size() != 0 && orders.size() != 0){
                showServicesInOrder(orders);
                showAllOrders();
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
    }
    private void showAllOrders(){
        ObservableList<Order> orders = FXCollections.observableArrayList(orderRepository.getAll());
        nameClient_col.setCellValueFactory(new PropertyValueFactory<>("fullNameCustomer"));
        startDate_col.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate_col.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        priceOrder_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        auto_col.setCellValueFactory(new PropertyValueFactory<>("auto"));
        table_orders.setItems(orders);
    }
    private void showServicesInOrder(ObservableList<Order> orders){
        ObservableList<Service> services = FXCollections.observableArrayList(orderRepository.getAll().stream().filter(x->x.getId().equals(orders.get(0).getId())).findFirst().get().getServiceList());
        nameService_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceService_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        services_table.setItems(services);
    }
    private void calculatePriceInOrder(ObservableList<Order> orders, int price, double sale){
        //handler.updatePriceOrder((int) (price - price * sale + orders.get(0).getPrice()), orders.get(0).getId());
    }
}
