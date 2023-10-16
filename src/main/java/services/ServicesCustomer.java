package services;

import model.Customer;
import model.Order;

import java.time.LocalDate;
import java.util.List;

public interface ServicesCustomer {
    List<Customer> getAll();
    void deleteLineById(int id);
    boolean orderContained(int id, List<Order> orders);
    String getNameById(int id);
    Customer save(String name, String secondName, String email, String phone, LocalDate date);
    void save(Customer customer);
}
