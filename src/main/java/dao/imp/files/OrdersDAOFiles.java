package dao.imp.files;


import common.Constants;
import configuration.Configuration;
import dao.OrdersDAO;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Order;
import model.errors.OrderError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class OrdersDAOFiles implements OrdersDAO {
    @Override
    public Either<OrderError, List<Order>> getAll() {
        Either<OrderError, List<Order>> result;
        Path orderFile = Paths.get(Configuration.getInstance().getProperty(Constants.PATH_ORDERS));
        List<Order> orders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orders.add(new Order(line));
            }
            result = Either.right(orders);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            result = Either.left(new OrderError(Constants.ERROR_WHILE_RETRIEVING_ORDERS));
        }
        return result;
    }

    public void updateOrder(int id, Order order) {
        Path orderFile = Paths.get(Configuration.getInstance().getProperty(Constants.PATH_ORDERS));
        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(orderFile));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(Integer.toString(id))) {
                    fileContent.set(i, order.toStringTextFile());
                }
            }
            Files.write((orderFile), fileContent);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public Either<OrderError, Order> addOrder(int id, LocalDateTime date, int customer_id, int table_id) {
        Either<OrderError, Order> result;
        if (validate(id, date, customer_id, table_id)) {
            Order order = new Order(id, date, customer_id, table_id);
            result = Either.right(order);
        } else {
            OrderError error = new OrderError(Constants.NO_VALID_DATA);
            result = Either.left(error);
        }
        return result;
    }

    private boolean validate(int id, LocalDateTime date, int customer_id, int table_id) {
        boolean result = false;
        if (id > 0 && date != null && customer_id > 0 && table_id > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public Either<OrderError, Order> save(LocalDateTime date, int customer_id, int table_id) {
        return Either.right(new Order(getNextOrderId().get(), date, customer_id, table_id));
    }

    public Either<OrderError, Integer> getNextOrderId() {
        Either<OrderError, Integer> result;
        try (BufferedReader br = new BufferedReader(new FileReader(Configuration.getInstance().getProperty(Constants.PATH_ORDERS)))) {
            int nextId = 1;
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0) {
                    int id = Integer.parseInt(parts[0]);
                    nextId = id + 1;
                }
            }
            result = Either.right(nextId);
        } catch (IOException e) {
            result = Either.left(new OrderError(Constants.ERROR_WHILE_RETRIEVING_ORDERS));
        }
        return result;
    }


    @Override
    public Either<OrderError, List<Order>> get(int id) {
        List<Order> list = this.getAll().getOrElse(new ArrayList<>());
        List<Order> filteredOrders = list.stream()
                .filter(order -> order.getCustomer_id() == id)
                .toList();
        return Either.right(filteredOrders);
    }

    @Override
    public Either<OrderError, List<Order>> get(LocalDate localDate) {
        List<Order> list = this.getAll().getOrElse(new ArrayList<>());
        List<Order> filteredOrders = list.stream()
                .filter(order -> order.getDate().toLocalDate().equals(localDate))
                .toList();
        return Either.right(filteredOrders);
    }

    @Override
    public void delete(int idToDelete) {
        try {
            Path path = Paths.get(Configuration.getInstance().getProperty(Constants.PATH_ORDERS));
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

    @Override
    public void save(Order order) {
        try {
            Path path = Paths.get(Configuration.getInstance().getProperty(Constants.PATH_ORDERS));
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
}