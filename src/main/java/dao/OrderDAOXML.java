package dao;

import io.vavr.control.Either;
import jakarta.xml.bind.JAXBException;
import model.errors.OrderError;
import model.xml.OrderItemXML;
import model.xml.OrderXML;

import java.io.IOException;
import java.util.List;

public interface OrderDAOXML {

    Either<OrderError, List<OrderXML>> getAll() throws IOException, JAXBException;

    Either<OrderError, List<OrderItemXML>> getAll(int id) throws JAXBException, IOException;

    void delete(int orderId);

    void save(OrderXML order);
}
