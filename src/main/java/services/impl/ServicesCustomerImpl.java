package services.impl;

import dao.CustomerDAO;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import model.Customer;
import model.Order;
import services.ServicesCustomer;

import java.util.List;

@Singleton
public class ServicesCustomerImpl implements ServicesCustomer {
    private final CustomerDAO customerDAO;

    @Inject
    public ServicesCustomerImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public List<Customer> getAll() {
        return customerDAO.getAll();
    }

    @Override
    public void deleteLineById(int id) {
        customerDAO.deleteLineById(id);
    }

    @Override
    public boolean orderContained(int id, List<Order> orders) {
        return customerDAO.orderContained(id,orders);
    }
}
