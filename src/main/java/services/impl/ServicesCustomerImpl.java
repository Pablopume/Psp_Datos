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
        customerDAO.delete(id);
    }

    public boolean orderContained(int id, List<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            if (id == orders.get(i).getCustomer_id()) {
                return true;
            }
        }
        return false;

    }
}