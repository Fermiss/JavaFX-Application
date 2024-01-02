package mongodbCollections;

import org.bson.types.ObjectId;

public class TypeCustomer {
    private ObjectId id;
    private String name;
    private double discount;

    public TypeCustomer(ObjectId id, String name, double discount) {
        this.id = id;
        this.name = name;
        this.discount = discount;
    }

    public TypeCustomer(String name, double discount) {
        this.name = name;
        this.discount = discount;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
