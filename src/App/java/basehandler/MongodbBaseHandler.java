package basehandler;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import org.bson.*;



public class MongodbBaseHandler {
    public static MongoCollection<Document> getCollection(String nameCollection){
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase mongoDatabase = client.getDatabase("kyrs");
        return mongoDatabase.getCollection(nameCollection);
    }

}


