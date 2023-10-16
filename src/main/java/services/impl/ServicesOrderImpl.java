package services.impl;

import dao.OrdersDAO;
import jakarta.inject.Inject;
import model.Order;
import services.ServicesOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public void writeToFile(Order order) {
        ordersDAO.writeToFile(order);
    }

    @Override
    public String orderToFileLine(Order order) {
        return ordersDAO.orderToFileLine(order);
    }
   public Order createOrder(int id, LocalDateTime date, int customer_id, int table_id){
        return ordersDAO.createOrder(id,date,customer_id,table_id);
   }
public List<Order> filteredList(int id){
        return ordersDAO.filteredList(id);
}

    @Override
    public List<Order> filteredListDate(LocalDate localDate) {
        return ordersDAO.filteredListDate(localDate);
    }

}
