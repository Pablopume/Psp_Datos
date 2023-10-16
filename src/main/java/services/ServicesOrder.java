package services;

import model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ServicesOrder {
    List<Order> getAll();
    void writeToFile(Order order);
    Order createOrder(LocalDateTime date, int customer_id, int table_id);
    List<Order> filteredList(int id);
    List<Order> filteredListDate(LocalDate localDate);
    void delete(int idToDelete);
    void deleteOrders(List<Order> listOrd, int id);
    boolean orderContained(int orderId);
}
