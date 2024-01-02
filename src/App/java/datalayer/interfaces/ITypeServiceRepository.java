package datalayer.interfaces;
import mongodbCollections.TypeService;

import java.util.List;

public interface ITypeServiceRepository {
    void add(TypeService typeService);
    void delete(TypeService typeService);
    void update(TypeService typeService);
    List<TypeService> getAll();
}
