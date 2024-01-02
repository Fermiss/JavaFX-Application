package mongodbCollections;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class Order {
    private ObjectId id;
    private List<Service> serviceList;
    private Date startDate;
    private Date endDate;
    private ObjectId id_customer;
    private String fullNameCustomer;
    private String phoneCustomer;
    private String nameBrand;
    private String nameModel;
    private int price;
    private String auto;

    public Order(ObjectId id, List<Service> serviceList, Date startDate, Date endDate, ObjectId id_customer, String fullNameCustomer, String phoneCustomer, String nameBrand, String nameModel, int price) {
        this.id = id;
        this.serviceList = serviceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id_customer = id_customer;
        this.fullNameCustomer = fullNameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.nameBrand = nameBrand;
        this.nameModel = nameModel;
        this.price = price;
    }

    public Order(Date startDate, ObjectId id_customer, String fullNameCustomer, String phoneCustomer, String nameBrand, String nameModel) {
        this.startDate = startDate;
        this.id_customer = id_customer;
        this.fullNameCustomer = fullNameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.nameBrand = nameBrand;
        this.nameModel = nameModel;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
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

    public ObjectId getId_customer() {
        return id_customer;
    }

    public void setId_customer(ObjectId id_customer) {
        this.id_customer = id_customer;
    }

    public String getFullNameCustomer() {
        return fullNameCustomer;
    }

    public void setFullNameCustomer(String fullNameCustomer) {
        this.fullNameCustomer = fullNameCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getNameBrand() {
        return nameBrand;
    }

    public void setNameBrand(String nameBrand) {
        this.nameBrand = nameBrand;
    }

    public String getNameModel() {
        return nameModel;
    }

    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuto(){
        return nameBrand + " " + nameModel;
    }
}
