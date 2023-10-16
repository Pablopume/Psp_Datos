package dao.imp.files;

import dao.CustomerDAO;

import lombok.extern.log4j.Log4j2;
import model.Customer;
import configuration.Configuration;
import model.Order;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Log4j2
public class CustomerDAOFiles implements CustomerDAO {


    @Override
    public List<Customer> getAll() {
        Path customerFile = Paths.get(Configuration.getInstance().getProperty("pathCustomers"));
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            List<String> fileContent = Files.readAllLines(customerFile);
            fileContent.forEach(line -> customers.add(new Customer(line)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return customers;
    }
    private int getNextCustomerId() {
        int nextId = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(Configuration.getInstance().getProperty("pathCustomers")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0) {
                    int id = Integer.parseInt(parts[0]);
                    nextId = id + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return nextId;
    }
    public Customer save(String name, String secondName, String email, String phone, LocalDate date) {
        return new Customer(getNextCustomerId(),name, secondName,email,phone,date );
    }


    public void save(Customer customer) {
        try {
            Path path = Paths.get(Configuration.getInstance().getProperty("pathCustomers"));
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
            Path path = Paths.get(Configuration.getInstance().getProperty("pathCustomers"));
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
