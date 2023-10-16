package dao.imp.files;

import configuration.ConfigXML;
import configuration.Configuration;
import dao.OrdersDAO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import model.Order;
import model.xml.OrderItemXML;
import model.xml.OrderXML;
import model.xml.OrdersXML;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class OrdersDAOFiles implements OrdersDAO {
    @Override
    public List<Order> getAll() {
        Path orderFile = Paths.get(Configuration.getInstance().getProperty("pathOrders"));
        ArrayList<Order> orders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orders.add(new Order(line));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return orders;
    }

    public void delete(int idToDelete) {
        try {
            Path path = Paths.get(Configuration.getInstance().getProperty("pathOrders"));
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

    public Order save(LocalDateTime date, int customer_id, int table_id) {
        return new Order(getNextOrderId(), date, customer_id, table_id);
    }

    private int getNextOrderId() {
        int nextId = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(Configuration.getInstance().getProperty("pathOrders")))) {
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


    public void save(Order order) {
        try {
            Path path = Paths.get(Configuration.getInstance().getProperty("pathOrders"));
            List<String> lines = new ArrayList<>();
            lines.add(order.toStringTextFile());


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

    public List<Order> get(int id) {
        List<Order> list = this.getAll();
        return list.stream()
                .filter(order -> order.getCustomer_id() == id)
                .toList();
    }

    public List<Order> get(LocalDate localDate) {
        List<Order> list = this.getAll();
        return list.stream()
                .filter(order -> order.getDate().toLocalDate().equals(localDate))
                .toList();
    }


}