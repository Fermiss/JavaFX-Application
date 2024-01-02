package datalayer;

import basehandler.MongodbBaseHandler;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import datalayer.interfaces.ICustomerRepository;
import mongodbCollections.Account;
import mongodbCollections.Customer;
import mongodbCollections.TypeCustomer;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CustomerRepository implements ICustomerRepository {
    @Override
    public void add(Customer customer) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("customer");
        Document document = new Document("fullName", customer.getFullName());
        document.append("mail", customer.getMail());
        document.append("phone", customer.getPhone());
        document.append("typeCustomer", new Document("name", customer.getTypeCustomer().getName())
                .append("discount", customer.getTypeCustomer().getDiscount()));
        document.append("account", new Document("login", customer.getAccount().getLogin())
                .append("password", customer.getAccount().getPassword()));
        collection.insertOne(document);
    }

    @Override
    public void delete(Customer customer) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("customer");
        Bson query = eq("_id", customer.getId());
        collection.deleteOne(query);
    }

    @Override
    public void update(Customer customer) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("customer");
        Document found = (Document) collection.find(new Document("_id",customer.getId()));
        Bson updatedValue = new Document("fullName", customer.getFullName())
                .append("mail", customer.getMail())
                .append("phone", customer.getPhone())
                .append("typeCustomer", new Document("name", customer.getTypeCustomer().getName()))
                .append("account", new Document("login", customer.getAccount().getLogin())
                .append("password", customer.getAccount().getPassword()));

        Bson updateOperation = new Document("$set", updatedValue);
        collection.updateOne(found, updateOperation);
    }

    @Override
    public List<Customer> getAll() {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("customer");
        List<Customer> list = new ArrayList<>();
        for (Document dc : collection.find()){
            Document doc = (Document) dc.get("account");
            Document doc2 = (Document) dc.get("typeCustomer");
            list.add(new Customer(dc.getString("fullName"),
                    dc.getString("mail"),
                    dc.getString("phone"),
                    new TypeCustomer(doc2.getString("name"), doc2.getDouble("discount")),
                    new Account(doc.getString("login"), doc.getString("password")),
                    dc.getObjectId("_id")));
        }
        return list;
    }
    }

