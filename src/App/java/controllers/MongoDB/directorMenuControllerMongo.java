package controllers.MongoDB;

import basehandler.MongodbBaseHandler;
import buisnesslayer.DirectorHelper;
import datalayer.BrandAutoRepository;
import datalayer.OrderRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mongodbCollections.Order;
import mongodbCollections.Service;

import java.util.ArrayList;
import java.util.List;

public class directorMenuControllerMongo {
    DirectorHelper directorHelper = new DirectorHelper();
    OrderRepository orderRepository = new OrderRepository();
    BrandAutoRepository brandAutoRepository = new BrandAutoRepository();

    @FXML
    private TableColumn<Service, Integer> money_col;

    @FXML
    private TableColumn<Service, String> name_col;

    @FXML
    private PieChart pieChartCategoryService;

    @FXML
    private PieChart pieChartMarkAuto;

    @FXML
    private Button statisticsCategoryServiceButton;

    @FXML
    private Button statisticsMarkAutoButton;

    @FXML
    private TableView<Service> table;

    @FXML
    private Button tableViewStatisticsByCategory;

    @FXML
    private Button tableViewStatisticsByMark;

    @FXML
    void initialize() {
        calculatePriceFromCategoryServices();
        tableViewStatisticsByCategory.setOnAction(event -> {
            pieChartMarkAuto.setVisible(false);
            pieChartCategoryService.setVisible(false);
            name_col.setText("Виды работ");
            table.setVisible(true);
            List<String> list = new ArrayList<>();
            orderRepository.getAll().forEach(x->{x.getServiceList().forEach(c->list.add(c.getName()));});
            ObservableList<Service> services = FXCollections.observableArrayList();
            for (String s : list) {
                services.add(new Service(directorHelper.calcService(s), s));
            }
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            money_col.setCellValueFactory(new PropertyValueFactory<>("price"));
            table.setItems(services);
        });
        statisticsCategoryServiceButton.setOnAction(event -> {
            calculatePriceFromCategoryServices();
        });
        statisticsMarkAutoButton.setOnAction(event -> {
            calculatePriceByMarkAuto();
        });
        tableViewStatisticsByMark.setOnAction(event -> {
            pieChartMarkAuto.setVisible(false);
            pieChartCategoryService.setVisible(false);
            table.setVisible(true);
            name_col.setText("Марка автомобиля");
            List<String> list = new ArrayList<>();
            brandAutoRepository.getAll().forEach(x->list.add(x.getName()));
            ObservableList<Service> services = FXCollections.observableArrayList();
            for (String s : list) {
                services.add(new Service(directorHelper.calcBrand(s), s));
            }
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            money_col.setCellValueFactory(new PropertyValueFactory<>("price"));
            table.setItems(services);
        });
    }
    private void calculatePriceFromCategoryServices(){
        pieChartCategoryService.setVisible(true);
        pieChartMarkAuto.setVisible(false);
        table.setVisible(false);
        List<String> list = new ArrayList<>();
        orderRepository.getAll().forEach(x->{x.getServiceList().forEach(c->list.add(c.getName()));});
        ObservableList<PieChart.Data> pieCharts = FXCollections.observableArrayList();
        for (String s : list) {
            pieCharts.add(new PieChart.Data(s, directorHelper.calcService(s)));
        }
        pieChartCategoryService.setTitle("Статистика по видам услуг и заработанных денег");
        pieChartCategoryService.setData(pieCharts);
    }
    private void calculatePriceByMarkAuto(){
        pieChartMarkAuto.setVisible(true);
        pieChartCategoryService.setVisible(false);
        table.setVisible(false);
        List<String> list = new ArrayList<>();
        brandAutoRepository.getAll().forEach(x->list.add(x.getName()));
        ObservableList<PieChart.Data> pieCharts = FXCollections.observableArrayList();
        for (String s : list) {
            pieCharts.add(new PieChart.Data(s, directorHelper.calcBrand(s)));
        }
        pieChartMarkAuto.setTitle("Статистика по маркам авто и заработанным деньгам");
        pieChartMarkAuto.setData(pieCharts);
    }


}

