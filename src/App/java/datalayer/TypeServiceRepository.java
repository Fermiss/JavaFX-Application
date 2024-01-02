package datalayer;

import basehandler.MongodbBaseHandler;
import com.mongodb.client.MongoCollection;
import datalayer.interfaces.ITypeServiceRepository;
import mongodbCollections.Service;
import mongodbCollections.TypeService;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.type;

public class TypeServiceRepository implements ITypeServiceRepository {
    @Override
    public void add(TypeService typeService) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("account");
        Document document = new Document("name", typeService.getName())
                .append("description", typeService.getDescription())
                .append("Service", typeService.getServices());
        collection.insertOne(document);
    }

    @Override
    public void delete(TypeService typeService) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("typeService");
        Bson query = eq("_id", typeService.getId());
        collection.deleteOne(query);
    }

    @Override
    public void update(TypeService typeService) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("typeService");
        Document found = (Document) collection.find(new Document("_id",typeService.getId()));
        Bson updatedValue = new Document("name", typeService.getName())
                .append("Service", typeService.getServices());
        Bson updateOperation = new Document("$set", updatedValue);
        collection.updateOne(found, updateOperation);
    }

    @Override
    public List<TypeService> getAll() {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("typeService");
        List<TypeService> serviceList = new ArrayList<>();
        for (Document dc: collection.find()) {
            List<Service> services = new ArrayList<>();
            for (Document dd : dc.getList("Service", Document.class)){
                services.add(new Service(dd.getInteger("price"),dd.getString("name")));
            }
            serviceList.add(new TypeService(dc.getObjectId("_id"), dc.getString("name"), dc.getString("description"),services));
        }
        return serviceList;
    }
}
