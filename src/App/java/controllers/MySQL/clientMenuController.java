package controllers.MySQL;

import java.io.IOException;
import java.sql.Date;
import basehandler.DataBaseHandler;
import controllers.clientOrderController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import usersClassesMySQL.Order;
import usersClassesMySQL.Service;
import usersClassesMySQL.TypeOfService;

public class clientMenuController {
    private String selectedDB;

    public void setCurrDB(String currDB){ selectedDB = currDB; }
    private DataBaseHandler handler = new DataBaseHandler();

    private String currLogin;

    public void setCurrLogin(String str){
        currLogin = str;
    }

    @FXML
    private Button clientsOrdersButton;

    @FXML
    private Button createOrderButton;

    @FXML
    private TableColumn<TypeOfService, String> description_col;

    @FXML
    private Button listOfTypesServicesButton;

    @FXML
    private TableColumn<TypeOfService, String> nameServiceType_col;

    @FXML
    private TableColumn<Service, String> nameService_col;

    @FXML
    private TableColumn<Service, Integer> priceService_col;

    @FXML
    private TableView<Service> service_table;

    @FXML
    private Button showServicesButton;

    @FXML
    private ChoiceBox<String> typeServiceChoiceBox;

    @FXML
    private TableView<TypeOfService> typesServices_table;

    @FXML
    private TableColumn<Order, String> auto_col;

    @FXML
    private TableColumn<Order, Date> endDate_col;

    @FXML
    private TableColumn<Service, String> nameServiceOrder_col;

    @FXML
    private TableView<Order> order_table;

    @FXML
    private TableColumn<Order, Integer> priceOrder_col;

    @FXML
    private TableColumn<Service, Integer> priceServiceOrder_col;

    @FXML
    private TableView<Service> servicesOrder_table;

    @FXML
    private TableColumn<Order, Date> startDate_col;

    @FXML
    private Separator separator;

    @FXML
    void mouseClickOrder(MouseEvent event) {
        ObservableList<Order> orders = order_table.getSelectionModel().getSelectedItems();
        if (orders.size()!=0) {
            showServicesInOrder(orders);
        }
    }

    @FXML
    void initialize() {
        typeServiceChoiceBox.getItems().addAll(handler.getAllTypesOfServices());
        showCategoryService();
        listOfTypesServicesButton.setOnAction(event -> {
            showCategoryService();
        });

        showServicesButton.setOnAction(event -> {
            service_table.setVisible(true);
            typesServices_table.setVisible(false);
            ObservableList<Service> services = handler.getAllServicesByCategory(typeServiceChoiceBox.getValue());
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
            controller.setCurrDB(selectedDB);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        clientsOrdersButton.setOnAction(event -> {
            showClientsOrders();
            ObservableList<Order> orders = new DataBaseHandler().getAllOrdersForCustomer(currLogin);
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
        ObservableList<TypeOfService> typeOfServices = handler.getAllTypeOfService();
        typesServices_table.setVisible(true);
        nameServiceType_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        typesServices_table.setItems(typeOfServices);
    }
    private void showServicesInOrder(ObservableList<Order> orders){
        ObservableList<Service> services = handler.getAllServicesInOrder(orders.get(0).getId());
        nameServiceOrder_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceServiceOrder_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        servicesOrder_table.setItems(services);
    }
}
