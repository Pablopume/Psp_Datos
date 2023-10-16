package dao;

import model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdersDAO {
    List<Order> getAll();
    void writeToFile(Order order);
    String orderToFileLine(Order order);
    Order createOrder(int id, LocalDateTime date, int customer_id, int table_id);
    List<Order> filteredList(int id);
    List<Order> filteredListDate(LocalDate localDate);

}
