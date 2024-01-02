package controllers.MySQL;

import basehandler.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import usersClassesMySQL.Service;


import java.util.List;

public class directorMenuController {
    private String selectedDB;

    public void setCurrDB(String currDB){ selectedDB = currDB; }

    DataBaseHandler handler = new DataBaseHandler();

    @FXML
    private TableView<Service> table;

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
            List<String> list = handler.getOrderedTypesServices();
            ObservableList<Service> services = FXCollections.observableArrayList();
            for (int i = 0; i < list.size(); i++) {
                services.add(new Service(handler.getSumOfCategoryService(list.get(i)), list.get(i)));
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
            List<String> list = handler.getOrderedMarkAuto();
            ObservableList<Service> services = FXCollections.observableArrayList();
            for (int i = 0; i < list.size(); i++) {
                services.add(new Service(handler.getSumOfMarkAutoBuName(list.get(i)),list.get(i)));
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
        List<String> list = handler.getOrderedTypesServices();
        ObservableList<PieChart.Data> pieCharts = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            pieCharts.add(new PieChart.Data(list.get(i),handler.getSumOfCategoryService(list.get(i))));
        }
        pieChartCategoryService.setTitle("Статистика по видам услуг и заработанных денег");
        pieChartCategoryService.setData(pieCharts);
    }
    private void calculatePriceByMarkAuto(){
        pieChartMarkAuto.setVisible(true);
        pieChartCategoryService.setVisible(false);
        table.setVisible(false);
        List<String> list = handler.getOrderedMarkAuto();
        ObservableList<PieChart.Data> pieCharts = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            pieCharts.add(new PieChart.Data(list.get(i),handler.getSumOfMarkAutoBuName(list.get(i))));
        }
        pieChartMarkAuto.setTitle("Статистика по маркам авто и заработанным деньгам");
        pieChartMarkAuto.setData(pieCharts);
    }
}
