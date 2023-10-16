package dao;

import model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdersDAO {
    List<Order> getAll();
    void save(Order order);
    Order save(int id, LocalDateTime date, int customer_id, int table_id);
    List<Order> get(int id);
    List<Order> get(LocalDate localDate);
    void delete(int idToDelete);

}
