package datalayer.interfaces;

import mongodbCollections.Customer;

import java.util.List;

public interface ICustomerRepository {
    void add(Customer customer);
    void delete(Customer customer);
    void update(Customer customer);
    List<Customer> getAll();
}
