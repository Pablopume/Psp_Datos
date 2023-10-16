package dao;

import jakarta.xml.bind.JAXBException;
import model.xml.OrderItemXML;
import model.xml.OrderXML;

import java.io.IOException;
import java.util.List;

public interface OrderDAOXML {

    List<OrderXML> getAll() throws IOException, JAXBException;
    List<OrderItemXML> getAll(int id) throws JAXBException, IOException;
    void delete(int orderId);
    void save(OrderXML order);
}
