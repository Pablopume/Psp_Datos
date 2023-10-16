package dao;

import jakarta.xml.bind.JAXBException;
import model.xml.OrderXML;

import java.io.IOException;
import java.util.List;

public interface OrderDAOXML {

    List<OrderXML> getAll() throws IOException, JAXBException;
}
