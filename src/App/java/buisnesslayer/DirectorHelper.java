package buisnesslayer;

import datalayer.OrderRepository;
import mongodbCollections.Order;
import mongodbCollections.Service;

import java.util.List;

public class DirectorHelper {
    OrderRepository orderRepository = new OrderRepository();
    public int calcService(String name){
        int i = 0;
        List<Order> orders = orderRepository.getAll();
        for (Order order : orders) {
            i += order.getServiceList().stream().filter(x -> x.getName().equals(name)).mapToInt(Service::getPrice).sum();
        }
        return i;
    }
    public int calcBrand(String name){
        int i = 0;
        List<Order> orders = orderRepository.getAll();
        for (Order order : orders){
            if (order.getNameBrand().equals(name)){
                i+= order.getPrice();
            }
        }
        return i;
    }
}
