package services;

import model.Customer;
import model.Order;

import java.util.List;

public interface ServicesCustomer {
    List<Customer> getAll();
    void deleteLineById(int id);
    boolean orderContained(int id, List<Order> orders);

}
