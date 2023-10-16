package dao;

import model.Customer;
import model.Order;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getAll();
    void delete(int id);
}

