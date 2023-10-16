package dao;

import model.Customer;
import model.Order;

import java.time.LocalDate;
import java.util.List;

public interface CustomerDAO {
    Customer save(String name, String secondName, String email, String phone, LocalDate date);
    List<Customer> getAll();
    void delete(int id);
    void save(Customer customer);
}

