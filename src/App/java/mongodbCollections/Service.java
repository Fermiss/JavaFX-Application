package mongodbCollections;

import org.bson.types.ObjectId;

public class Service {
    private int price;
    private String name;

    public Service(int price, String name) {
        this.price = price;
        this.name = name;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
