package datalayer.interfaces;

import mongodbCollections.Order;

import java.util.List;

public interface IOrderRepository {
    void add(Order order);
    void delete(Order order);
    void update(Order order);
    List<Order> getAll();
}
