package dao.imp.files;

import dao.CustomerDAO;

import lombok.extern.log4j.Log4j2;
import model.Customer;
import configuration.Configuration;
import model.Order;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
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
