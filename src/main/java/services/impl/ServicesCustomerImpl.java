package services.impl;

import dao.CustomerDAO;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import model.Customer;
import model.Order;
import services.ServicesCustomer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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


    public String getNameById(int id) {
        List<Customer> customerList = customerDAO.getAll();
        Optional<Customer> customer = customerList.stream().filter(customer1 -> customer1.getId() == id).findFirst();
        Customer customer2 = customer.orElse(null);
        String s="No hay usuario con ese id";
        if (customer2 != null) {
            s=customer2.getFirst_name();
        }
        return s;
    }

    @Override
    public Customer save(String name, String secondName, String email, String phone, LocalDate date) {
        return customerDAO.save(name,secondName,email,phone,date);
    }

    @Override
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    public boolean orderContained(int id, List<Order> orders) {
        boolean contained = false;
        for (Order order : orders) {
            if (id == order.getCustomer_id()) {
                contained = true;
            }
        }
        return contained;

    }
}