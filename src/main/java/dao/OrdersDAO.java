package dao;

import model.Order;

import java.util.List;

public interface OrdersDAO {
    List<Order> getAll();
}
