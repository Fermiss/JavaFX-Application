package mongodbCollections;

import org.bson.types.ObjectId;

public class Customer {
    private ObjectId id;
    private Account account;
    private String fullName;
    private String mail;
    private String phone;
    private TypeCustomer typeCustomer;

    public Customer(String fullName, String mail, String phone, TypeCustomer typeCustomer, Account account, ObjectId id) {
        this.id = id;
        this.fullName = fullName;
        this.mail = mail;
        this.phone = phone;
        this.typeCustomer = typeCustomer;
        this.account = account;
    }
    public Customer(String fullName, String mail, String phone, TypeCustomer typeCustomer, Account account) {
        this.fullName = fullName;
        this.mail = mail;
        this.phone = phone;
        this.typeCustomer = typeCustomer;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TypeCustomer getTypeCustomer() {
        return typeCustomer;
    }

    public void setTypeCustomer(TypeCustomer typeCustomer) {
        this.typeCustomer = typeCustomer;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
