package buisnesslayer;

import datalayer.TypeServiceRepository;
import mongodbCollections.TypeService;

import java.util.List;

public class CustomerHelper {
    TypeServiceRepository typeServiceRepository = new TypeServiceRepository();
    public List<TypeService> searchService(String search){
        return typeServiceRepository.getAll().stream().filter(x->x.getName().equals(search)).toList();
    }
}
