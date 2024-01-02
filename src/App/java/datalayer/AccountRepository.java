package datalayer;

import basehandler.MongodbBaseHandler;
import com.mongodb.client.MongoCollection;
import datalayer.interfaces.IAccountRepository;
import mongodbCollections.Account;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AccountRepository implements IAccountRepository {
    @Override
    public void add(Account account) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("account");
        Document document = new Document("login", account.getLogin());
        document.append("password", account.getPassword());
        document.append("role", account.getRole());
        collection.insertOne(document);
    }

    @Override
    public void delete(Account account) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("account");
        Bson query = eq("login", account.getLogin());
        collection.deleteOne(query);
    }

    @Override
    public void update(Account account) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("account");
        Document found = (Document) collection.find(new Document("_id",account.getId()));
        Bson updatedValue = new Document("login", account.getLogin())
                .append("password", account.getPassword())
                .append("role", account.getRole());
        Bson updateOperation = new Document("$set", updatedValue);
        collection.updateOne(found, updateOperation);
    }

    @Override
    public List<Account> getAll() {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("account");
        List<Account> list = new ArrayList<>();
        for (Document dc : collection.find()){
            list.add(new Account(dc.getObjectId("_id"),dc.getString("login"),dc.getString("password"),dc.getString("role")));
        }
        return list;
    }

}
