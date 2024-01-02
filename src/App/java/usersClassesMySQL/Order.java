package usersClassesMySQL;

import basehandler.DataBaseHandler;

import java.sql.Date;

public class Order {
    private int id;
    private int customer_id;
    private Date startDate;
    private Date endDate;
    private int price;
    private int modelAutoId;
    private String auto;
    private String fullNameCustomer;
    public Order(Date startDate, Date endDate, int price, int modelAutoId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.modelAutoId = modelAutoId;
    }

    public Order(int id, int customer_id, Date startDate, Date endDate, int price, int modelAutoId) {
        this.id = id;
        this.customer_id = customer_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.modelAutoId = modelAutoId;
        auto = new DataBaseHandler().getFullNameAuto(modelAutoId);
        fullNameCustomer = new DataBaseHandler().getNameCustomerById(customer_id);
    }

    public Order(Date startDate, int customer_id, int modelAutoId) {
        this.customer_id = customer_id;
        this.startDate = startDate;
        this.modelAutoId = modelAutoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getModelAutoId() {
        return modelAutoId;
    }

    public void setModelAutoId(int modelAutoId) {
        this.modelAutoId = modelAutoId;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getFullNameCustomer() {
        return fullNameCustomer;
    }

    public void setFullNameCustomer(String fullNameCustomer) {
        this.fullNameCustomer = fullNameCustomer;
    }
}
