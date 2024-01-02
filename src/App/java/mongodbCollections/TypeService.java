package mongodbCollections;


import org.bson.types.ObjectId;

import java.util.List;

public class TypeService {
    private ObjectId id;
    private String name;
    private String description;
    private List<Service> services;

    public TypeService(ObjectId id, String name,String description, List<Service> services) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.services = services;
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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
