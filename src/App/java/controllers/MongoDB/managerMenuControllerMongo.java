package controllers.MongoDB;

import basehandler.MongodbBaseHandler;
import datalayer.CustomerRepository;
import datalayer.TypeServiceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mongodbCollections.Customer;
import mongodbCollections.Service;
import mongodbCollections.TypeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class managerMenuControllerMongo {
    CustomerRepository customerRepository = new CustomerRepository();
    TypeServiceRepository typeServiceRepository = new TypeServiceRepository();

    List<String> typeOfCustomers = new ArrayList<>();
    @FXML
    private Button showCustomersButton;

    @FXML
    private Button changeStatusCustomerButton;

    @FXML
    private TableView<Customer> customers_table;

    @FXML
    private TableColumn<Customer, Integer> moneyCustomer_col;

    @FXML
    private TableColumn<Customer, String> emailCustomer_col;

    @FXML
    private Button goToAuthButton;

    @FXML
    private TableColumn<Customer, String> nameCustomer_col;

    @FXML
    private TableColumn<Customer, String> phoneCustomer_col;

    @FXML
    private Button showTypesOfServicesButton;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private TableColumn<Customer, String> statusCustomer_col;

    @FXML
    private Label label1;

    @FXML
    private Button addNewServiceButton;

    @FXML
    private TableColumn<TypeService, String> description_col;

    @FXML
    private TextField nameNewServiceTextFiled;

    @FXML
    private TableColumn<TypeService, String> nameServiceType_col;

    @FXML
    private TableColumn<Service, String> nameService_col;

    @FXML
    private TextField priceNewServiceTextField;

    @FXML
    private TableColumn<Service, Integer> priceService_col;

    @FXML
    private TableView<Service> service_table;

    @FXML
    private Button showServicesButton;

    @FXML
    private ChoiceBox<String> typeServiceChoiceBox;

    @FXML
    private TableView<TypeService> typesServices_table;

    @FXML
    void initialize() {
        List<String> lst = new ArrayList<>();
        typeServiceRepository.getAll().forEach(x->lst.add(x.getName()));
        typeServiceChoiceBox.getItems().addAll(lst);
        statusChoiceBox.getItems().addAll(typeOfCustomers);
        showCustomers();
        showCustomersButton.setOnAction(event -> {
            showCustomers();
        });
        changeStatusCustomerButton.setOnAction(event -> {
            ObservableList<Customer> customers = customers_table.getSelectionModel().getSelectedItems();
            if (statusChoiceBox.getValue() != null || customers.size() != 0){
                //MongodbBaseHandler.updateCustomer();
                showCustomers();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Осторожно");
                alert.setContentText("Вы не указали новый статус клиента или не выбрали клиента");
                alert.showAndWait();
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
        showTypesOfServicesButton.setOnAction(event -> {
            showCategoryService();
        });
        showServicesButton.setOnAction(event -> {
            showServices();
        });
        addNewServiceButton.setOnAction(event -> {
            if (typeServiceChoiceBox.getValue() != null || !priceNewServiceTextField.getText().equals("") || !nameNewServiceTextFiled.getText().equals("")) {
                Service service = new Service(Integer.parseInt(priceNewServiceTextField.getText().trim()), nameNewServiceTextFiled.getText().trim());
                TypeService typeService = typeServiceRepository.getAll().stream().filter(x->x.getName().equals(typeServiceChoiceBox.getValue())).toList().get(0);
                //typeServiceRepository.update();
                showServices();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Вы не заполнили необходимые поля для заказа");
                alert.showAndWait();
            }
        });
    }
    private void showCustomers(){
        addNewServiceButton.setVisible(false);
        nameNewServiceTextFiled.setVisible(false);
        priceNewServiceTextField.setVisible(false);
        service_table.setVisible(false);
        typesServices_table.setVisible(false);
        typeServiceChoiceBox.setVisible(false);
        showServicesButton.setVisible(false);

        label1.setVisible(true);
        customers_table.setVisible(true);
        statusChoiceBox.setVisible(true);
        changeStatusCustomerButton.setVisible(true);

        ObservableList<Customer> customers = FXCollections.observableArrayList();
             customers.addAll(customerRepository.getAll());
        nameCustomer_col.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneCustomer_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCustomer_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        moneyCustomer_col.setCellValueFactory(new PropertyValueFactory<>("money"));
        statusCustomer_col.setCellValueFactory(new PropertyValueFactory<>("customerType_str"));
        customers_table.setItems(customers);
    }
    private void showCategoryService(){
        addNewServiceButton.setVisible(true);
        nameNewServiceTextFiled.setVisible(true);
        priceNewServiceTextField.setVisible(true);
        typesServices_table.setVisible(true);
        typeServiceChoiceBox.setVisible(true);
        showServicesButton.setVisible(true);

        service_table.setVisible(false);
        label1.setVisible(false);
        customers_table.setVisible(false);
        statusChoiceBox.setVisible(false);
        changeStatusCustomerButton.setVisible(false);

        ObservableList<TypeService> typeOfServices = FXCollections.observableArrayList();
        typeOfServices.addAll(typeServiceRepository.getAll());
        typesServices_table.setVisible(true);
        nameServiceType_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        typesServices_table.setItems(typeOfServices);
    }
    private void showServices(){
        service_table.setVisible(true);
        typesServices_table.setVisible(false);

        ObservableList<Service> services = FXCollections.observableArrayList();
        services.addAll(typeServiceRepository.getAll().stream().filter(x->x.getName().equals(typeServiceChoiceBox.getValue())).findFirst().get().getServices());
        nameService_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceService_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        service_table.setItems(services);
    }
}

