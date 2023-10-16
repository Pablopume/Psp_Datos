package services;

import io.vavr.control.Either;
import model.Customer;
import model.Order;
import model.errors.CustomerError;

import java.time.LocalDate;
import java.util.List;

public interface ServicesCustomer {
    Either<CustomerError, List<Customer>> getAll();

    void deleteLineById(int id);

    Either<CustomerError, Boolean> orderContained(int id, List<Order> orders);

    Either<CustomerError, String> getNameById(int id);

    Either<CustomerError, Customer> save(String name, String secondName, String email, String phone, LocalDate date);

    void save(Customer customer);

    void updateCustomer(int id, Customer customer);

    Either<CustomerError, Customer> addCustomer(int id, String first, String second, String mail, String phone, LocalDate dob);
}
