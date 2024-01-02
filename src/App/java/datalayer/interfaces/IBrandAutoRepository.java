package datalayer.interfaces;

import mongodbCollections.BrandAuto;

import java.util.List;

public interface IBrandAutoRepository {
    void add(BrandAuto brandAuto);
    void delete(BrandAuto brandAuto);
    void update(BrandAuto brandAuto);
    List<BrandAuto> getAll();
}
