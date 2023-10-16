package dao;

import io.vavr.control.Either;
import model.Customer;
import model.errors.CustomerError;

import java.time.LocalDate;
import java.util.List;

public interface CustomerDAO {
    Either<CustomerError, Customer> save(String name, String secondName, String email, String phone, LocalDate date);

    Either<CustomerError, List<Customer>> getAll();

    void delete(int id);

    void save(Customer customer);

    void updateCustomer(int id, Customer customer);

    Either<CustomerError, Customer> addCustomer(int id, String first, String second, String mail, String phone, LocalDate dob);
}

