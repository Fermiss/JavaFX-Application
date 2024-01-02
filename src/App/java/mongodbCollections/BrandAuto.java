package mongodbCollections;

import org.bson.types.ObjectId;

import java.util.List;

public class BrandAuto {
    private ObjectId id;
    private String name;
    private List<String> models;

    public BrandAuto(ObjectId id, String name, List<String> models) {
        this.id = id;
        this.name = name;
        this.models = models;
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

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }
}
