package datalayer;

import basehandler.MongodbBaseHandler;
import com.mongodb.client.MongoCollection;
import datalayer.interfaces.IOrderRepository;
import mongodbCollections.Order;
import mongodbCollections.Service;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class OrderRepository implements IOrderRepository {
    @Override
    public void add(Order order) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("Order");;
        Document document = new Document("service", new ArrayList<>());
        document.append("startDate", order.getStartDate());
        document.append("endDate", null);
        document.append("idCustomer", order.getId_customer());
        document.append("fullNameCustomer", order.getFullNameCustomer());
        document.append("nameBrand",order.getNameBrand());
        document.append("nameModel", order.getNameModel());
        document.append("price", order.getPrice());
        document.append("phoneCustomer", order.getPhoneCustomer());
        collection.insertOne(document);
    }

    @Override
    public void delete(Order order) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("Order");
        Bson query = eq("_id", order.getId());
        collection.deleteOne(query);
    }

    @Override
    public void update(Order order) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("Order");
        Document found = (Document) collection.find(new Document("_id",order.getId()));
        Bson updatedValue = new Document("service", order.getServiceList())
                .append("endDate", order.getEndDate())
                .append("price", order.getPrice());

        Bson updateOperation = new Document("$set", updatedValue);
        collection.updateOne(found, updateOperation);
    }

    @Override
    public List<Order> getAll() {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("Order");
        List<Order> list = new ArrayList<>();
        for (Document dc : collection.find()){
            List<Service> services = new ArrayList<>();
            for (Document dd : dc.getList("service", Document.class)){
                services.add(new Service(dd.getInteger("price"),dd.getString("name")));
            }
            list.add(new Order(
                    dc.getObjectId("_id"),
                    services,
                    dc.getDate("startDate"),
                    dc.getDate("endDate"),
                    dc.getObjectId("idCustomer"),
                    dc.getString("fullNameCustomer"),
                    dc.getString("phoneCustomer"),
                    dc.getString("nameBrand"),
                    dc.getString("nameModel"),
                    dc.getInteger("price")));
        }
        return list;
    }
}
