package services;

import jakarta.xml.bind.JAXBException;
import model.xml.OrderItemXML;
import model.xml.OrderXML;

import java.io.IOException;
import java.util.List;

public interface ServicesDaoXML {
    List<OrderXML> getAll() throws IOException, JAXBException;
    List<OrderItemXML> getAll(int id) throws JAXBException, IOException;

    void delete(int orderId);
     void writeToXML(OrderXML order);
}
