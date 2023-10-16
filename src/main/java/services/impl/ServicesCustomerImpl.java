package services.impl;

import dao.CustomerDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import model.Customer;
import model.Order;
import model.errors.CustomerError;
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
    public Either<CustomerError, List<Customer>> getAll() {
        return customerDAO.getAll();
    }

    @Override
    public void deleteLineById(int id) {
        customerDAO.delete(id);
    }

    public Either<CustomerError, String> getNameById(int id) {
        return customerDAO.getAll().map(customerList -> {
            Optional<Customer> customer = customerList.stream()
                    .filter(customer1 -> customer1.getId() == id)
                    .findFirst();
            return customer.map(Customer::getFirst_name).orElse("No hay usuario con ese id");
        });
    }

    @Override
    public Either<CustomerError, Customer> save(String name, String secondName, String email, String phone, LocalDate date) {
        return customerDAO.save(name, secondName, email, phone, date);
    }

    @Override
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public void updateCustomer(int id, Customer customer) {
        customerDAO.updateCustomer(id, customer);
    }

    @Override
    public Either<CustomerError, Customer> addCustomer(int id, String first, String second, String mail, String phone, LocalDate dob) {
        return customerDAO.addCustomer(id, first, second, mail, phone, dob);
    }

    public Either<CustomerError, Boolean> orderContained(int id, List<Order> orders) {
        boolean contained = orders.stream().anyMatch(order -> id == order.getCustomer_id());
        return Either.right(contained);
    }
}

