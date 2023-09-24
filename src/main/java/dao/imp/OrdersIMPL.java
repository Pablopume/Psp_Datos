package dao.imp;

import dao.OrdersDAO;
import model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersIMPL implements OrdersDAO {
    private final List<Order> listOrd;

    public OrdersIMPL() {
        this.listOrd = new ArrayList<>();
        this.listOrd.add(new Order(1, LocalDateTime.of(2023, 9, 24, 10, 0), 101, 1));
        this.listOrd.add(new Order(2, LocalDateTime.of(2023, 9, 24, 12, 30), 102, 2));
        this.listOrd.add(new Order(3, LocalDateTime.of(2023, 9, 25, 15, 15), 103, 3));
    }


    public List<Order> getAll() {
        return listOrd;
    }
}
