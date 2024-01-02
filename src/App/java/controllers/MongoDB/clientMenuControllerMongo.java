package controllers.MongoDB;
import basehandler.MongodbBaseHandler;
import controllers.clientOrderController;
import datalayer.CustomerRepository;
import datalayer.OrderRepository;
import datalayer.TypeServiceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mongodbCollections.*;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class clientMenuControllerMongo {
    TypeServiceRepository typeServiceRepository = new TypeServiceRepository();
    OrderRepository orderRepository = new OrderRepository();
    CustomerRepository customerRepository = new CustomerRepository();

    private String currLogin;

    public void setCurrLogin(String str){
        currLogin = str;
    }
    @FXML
    private TableColumn<Order, String> auto_col;

    @FXML
    private Button clientsOrdersButton;

    @FXML
    private Button createOrderButton;

    @FXML
    private TableColumn<TypeService, String> description_col;

    @FXML
    private TableColumn<Order, Date> endDate_col;

    @FXML
    private Button listOfTypesServicesButton;

    @FXML
    private TableColumn<Service, String> nameServiceOrder_col;

    @FXML
    private TableColumn<Service, String> nameServiceType_col;

    @FXML
    private TableColumn<Service, String> nameService_col;

    @FXML
    private TableView<Order> order_table;

    @FXML
    private TableColumn<Order, Integer> priceOrder_col;

    @FXML
    private TableColumn<Order, Integer> priceServiceOrder_col;

    @FXML
    private TableColumn<Order, Integer> priceService_col;

    @FXML
    private Separator separator;

    @FXML
    private TableView<Service> service_table;

    @FXML
    private TableView<Service> servicesOrder_table;

    @FXML
    private Button showServicesButton;

    @FXML
    private TableColumn<Order, Date> startDate_col;

    @FXML
    private ChoiceBox<String> typeServiceChoiceBox;

    @FXML
    private TableView<TypeService> typesServices_table;

    @FXML
    void mouseClickOrder(MouseEvent event) {
        ObservableList<Order> orders = order_table.getSelectionModel().getSelectedItems();
        if (orders.size()!=0) {
            showServicesInOrder(orders);
        }
    }

    @FXML
    void initialize() {
        List<String> itemsForChoiceBox = new ArrayList<>();
        List<TypeService> serviceList = typeServiceRepository.getAll();
        serviceList.forEach(x->itemsForChoiceBox.add(x.getName()));
        typeServiceChoiceBox.getItems().addAll(itemsForChoiceBox);
        showCategoryService();
        listOfTypesServicesButton.setOnAction(event -> {
            showCategoryService();
        });

        showServicesButton.setOnAction(event -> {
            service_table.setVisible(true);
            typesServices_table.setVisible(false);
            ObservableList<Service> services = FXCollections.observableArrayList(typeServiceRepository.getAll().stream().filter(x->x.getName().equals(typeServiceChoiceBox.getValue())).findFirst().get().getServices());
            nameService_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceService_col.setCellValueFactory(new PropertyValueFactory<>("price"));
            service_table.setItems(services);
        });
        createOrderButton.setOnAction(event -> {
            createOrderButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clientOrder.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            clientOrderController controller = loader.getController();
            controller.setCurrLogin(currLogin);
            controller.setCurrDB("MongoDB");
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        clientsOrdersButton.setOnAction(event -> {
            showClientsOrders();
            ObjectId customerId = customerRepository.getAll().stream().filter(x-> x.getAccount().getLogin().equals(currLogin)).findFirst().get().getId();
            List<Order> customersOrders = orderRepository.getAll().stream().filter(x-> x.getId_customer().equals(customerId)).toList();
            ObservableList<Order> orders = FXCollections.observableArrayList();
            orders.addAll(customersOrders);
            startDate_col.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endDate_col.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            auto_col.setCellValueFactory(new PropertyValueFactory<>("auto"));
            priceOrder_col.setCellValueFactory(new PropertyValueFactory<>("price"));
            order_table.setItems(orders);
        });
    }
    private void showClientsOrders(){
        separator.setVisible(true);
        showServicesButton.setVisible(false);
        typeServiceChoiceBox.setVisible(false);
        service_table.setVisible(false);
        typesServices_table.setVisible(false);
        order_table.setVisible(true);
        servicesOrder_table.setVisible(true);
    }
    private void showCategoryService(){
        separator.setVisible(false);
        order_table.setVisible(false);
        servicesOrder_table.setVisible(false);
        typeServiceChoiceBox.setVisible(true);
        showServicesButton.setVisible(true);
        typesServices_table.setVisible(true);
        service_table.setVisible(false);
        ObservableList<TypeService> typeOfServices = FXCollections.observableArrayList(typeServiceRepository.getAll());
        typesServices_table.setVisible(true);
        nameServiceType_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        typesServices_table.setItems(typeOfServices);
    }
    private void showServicesInOrder(ObservableList<Order> orders){
        ObservableList<Service> services = FXCollections.observableArrayList();
        Optional<Order> order = orderRepository.getAll().stream().filter(x->x.getId().equals(orders.get(0).getId())).findFirst();
        services.addAll(order.get().getServiceList());
        nameServiceOrder_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceServiceOrder_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        servicesOrder_table.setItems(services);
    }

}

