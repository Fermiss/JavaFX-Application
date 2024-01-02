package usersClassesMySQL;

import basehandler.DataBaseHandler;

public class Customer {
    private String fullName;
    private String phone;
    private String email;
    private int account_id;
    private int customerType_id;
    private String customerType_str;
    private int id;
    private int money;
    public Customer(String fullName, String phone, String email, int account_id, int customerType_id) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.account_id = account_id;
        this.customerType_id = customerType_id;
    }
    public Customer(String fullName, String phone, String email, int account_id, int customerType_id, int id) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.account_id = account_id;
        this.customerType_id = customerType_id;
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCustomerType_id() {
        return customerType_id;
    }

    public void setCustomerType_id(int customerType_id) {
        this.customerType_id = customerType_id;
    }

    public String getCustomerType_str() {
        return new DataBaseHandler().getStrStatusCustomer(customerType_id);
    }

    public void setCustomerType_str(String customerType_str) {
        this.customerType_str = customerType_str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney(){
        return new DataBaseHandler().getPayedMoney(id);
    }
}
