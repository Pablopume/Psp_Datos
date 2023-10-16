package dao.imp.files;

import common.Constants;
import dao.CustomerDAO;

import lombok.extern.log4j.Log4j2;
import model.Customer;
import configuration.Configuration;
import model.errors.CustomerError;
import io.vavr.control.Either;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomerDAOFiles implements CustomerDAO {

    public Either<CustomerError, List<Customer>> getAll() {
        Either<CustomerError, List<Customer>> resulting;
        Path customerFile = Paths.get(Configuration.getInstance().getProperty(Constants.PATH_CUSTOMERS));
        List<Customer> customers = new ArrayList<>();
        try {
            List<String> fileContent = Files.readAllLines(customerFile);
            fileContent.forEach(line -> {
                Either<CustomerError, Customer> result = parseCustomer(line);
                result.peek(customers::add);
            });
            resulting = Either.right(customers);
        } catch (IOException e) {
            log.error(e.getMessage(), e);

            resulting = Either.left(new CustomerError(100, Constants.THE_CUSTOMER_LIST_IS_EMPTY));
        }
        return resulting;
    }

    public Either<CustomerError, Customer> addCustomer(int id, String first, String second, String mail, String phone, LocalDate dob) {
        Either<CustomerError, Customer> result;
        if (validate(first, second, mail, phone, dob)) {
            Customer customer = new Customer(id, first, second, mail, phone, dob);
            result = Either.right(customer);
        } else {
            CustomerError error = new CustomerError(100, Constants.CREATED_INCORRECTLY);
            result = Either.left(error);
        }
        return result;
    }

    private boolean validate(String first, String second, String mail, String phone, LocalDate dob) {
        boolean result = false;
        if (first != null && !first.isEmpty() &&
                second != null && !second.isEmpty() &&
                mail != null && !mail.isEmpty() &&
                phone != null && !phone.isEmpty() &&
                dob != null) {
            result = true;
        }
        return result;
    }

    private Either<CustomerError, Customer> parseCustomer(String line) {
        Either<CustomerError, Customer> result;
        try {
            Customer customer = new Customer(line);
            result = Either.right(customer);
        } catch (Exception e) {
            result = Either.left(new CustomerError(100, Constants.THE_CUSTOMER_LIST_IS_EMPTY));
        }
        return result;
    }

    private Either<CustomerError, Integer> getNextCustomerId() {
        Either<CustomerError, Integer> result;
        String line;
        int nextId = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(Configuration.getInstance().getProperty(Constants.PATH_CUSTOMERS)))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0) {
                    int id = Integer.parseInt(parts[0]);
                    nextId = id + 1;
                }
            }
            result = Either.right(nextId);
        } catch (IOException e) {
            result = Either.left(new CustomerError(100, Constants.THE_CUSTOMER_LIST_IS_EMPTY));
        }
        return result;
    }

    public void updateCustomer(int id, Customer customer) {
        Path customerFile = Paths.get(Configuration.getInstance().getProperty(Constants.PATH_CUSTOMERS));
        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(customerFile));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(Integer.toString(id))) {
                    fileContent.set(i, customer.toStringTextFile());
                }
            }

            Files.write((customerFile), fileContent);
        } catch (IOException e) {
            System.out.println(Constants.AN_ERROR_OCCURRED);
            e.printStackTrace();
        }
    }

    public Either<CustomerError, Customer> save(String name, String secondName, String email, String phone, LocalDate date) {
        return getNextCustomerId()
                .map(nextId -> new Customer(nextId, name, secondName, email, phone, date))
                .mapLeft(error -> new CustomerError(100, error.getMessage()));
    }

    public void save(Customer customer) {
        try {
            Path path = Paths.get(Configuration.getInstance().getProperty(Constants.PATH_CUSTOMERS));
            List<String> lines = new ArrayList<>();
            lines.add(customer.toStringTextFile());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void delete(int idToDelete) {
        try {
            Path path = Paths.get(Configuration.getInstance().getProperty(Constants.PATH_CUSTOMERS));
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split(";");
                int currentId = Integer.parseInt(parts[0]);
                if (currentId != idToDelete) {
                    updatedLines.add(line);
                }
            }
            Files.write(path, updatedLines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
