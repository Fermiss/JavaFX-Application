package datalayer;

import basehandler.MongodbBaseHandler;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import datalayer.interfaces.IBrandAutoRepository;
import mongodbCollections.BrandAuto;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BrandAutoRepository implements IBrandAutoRepository {
    @Override
    public void add(BrandAuto brandAuto) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("brandAuto");
        Document document = new Document("name", brandAuto.getName());
        document.append("models", brandAuto.getModels());
        collection.insertOne(document);
    }

    @Override
    public void delete(BrandAuto brandAuto) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("brandAuto");
        Bson query = eq("_id", brandAuto.getId());
        collection.deleteOne(query);
    }

    @Override
    public void update(BrandAuto brandAuto) {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("brandAuto");
        Document found = (Document) collection.find(new Document("_id",brandAuto.getId()));
        Bson updatedValue = new Document("name", brandAuto.getName())
                .append("models", brandAuto.getModels());
        Bson updateOperation = new Document("$set", updatedValue);
        collection.updateOne(found, updateOperation);
    }

    @Override
    public List<BrandAuto> getAll() {
        MongoCollection<Document> collection = MongodbBaseHandler.getCollection("brandAuto");
        List<BrandAuto> list = new ArrayList<>();
        for (Document dc : collection.find()){
            List<String> models = new ArrayList<>();
            list.add(new BrandAuto(dc.getObjectId("_id"),dc.getString("name"),dc.getList("models", String.class)));
        }
        return list;
    }
}
