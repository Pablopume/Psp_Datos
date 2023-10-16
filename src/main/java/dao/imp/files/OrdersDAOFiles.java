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

import java.io.File;
import java.io.IOException;
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
        Path orderrFile = Paths.get(Configuration.getInstance().getProperty("pathOrders"));
        ArrayList<Order> orders = new ArrayList<>();
        try {
            List<String> fileContent = Files.readAllLines(orderrFile);
            fileContent.forEach(line -> orders.add(new Order(line)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return orders;
    }

    public Order createOrder(int id, LocalDateTime date, int customer_id, int table_id) {

        return new Order(id, date, customer_id, table_id);

    }

    public void writeToFile(Order order) {
        try {
            Path path = Paths.get(Configuration.getInstance().getProperty("pathOrders"));
            List<String> lines = new ArrayList<>();
            lines.add(order.toStringTextFile());
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores, puedes personalizarlo según tu necesidad
        }
    }

    public List<Order> filteredList(int id) {
        List<Order> list = this.getAll();
        return list.stream()
                .filter(order -> order.getCustomer_id() == id)
                .toList();
    }

    public List<Order> filteredListDate(LocalDate localDate) {
        List<Order> list = this.getAll();
        return list.stream()
                .filter(order -> order.getDate().toLocalDate().equals(localDate))
                .toList();
    }

    public String orderToFileLine(Order order) {
        StringBuilder lineBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        lineBuilder.append(order.getId()).append(";");
        lineBuilder.append(order.getDate().format(formatter)).append(";");
        lineBuilder.append(order.getCustomer_id()).append(";");
        lineBuilder.append(order.getTable_id());
        return lineBuilder.toString();
    }

}

