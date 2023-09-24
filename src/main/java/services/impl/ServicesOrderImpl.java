package services.impl;

import dao.OrdersDAO;
import jakarta.inject.Inject;
import model.Order;
import services.ServicesOrder;

import java.util.List;

public class ServicesOrderImpl implements ServicesOrder {
    private OrdersDAO ordersDAO;
    @Inject
    public ServicesOrderImpl(OrdersDAO ordersDAO) {
        this.ordersDAO = ordersDAO;
    }
    public List<Order> getAll() {
        return ordersDAO.getAll();
    }

    ;
}
