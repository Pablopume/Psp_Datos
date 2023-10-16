package services.impl;

import dao.OrderDAOXML;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import model.errors.OrderError;
import model.xml.OrderItemXML;
import model.xml.OrderXML;

import java.io.IOException;
import java.util.List;

public class ServicesDaoXML implements services.ServicesDaoXML {
    private final OrderDAOXML dao;

    @Inject
    public ServicesDaoXML(OrderDAOXML dao) {
        this.dao = dao;
    }

    @Override
    public Either<OrderError, List<OrderXML>> getAll() throws IOException, JAXBException {
        return dao.getAll();
    }

    @Override
    public Either<OrderError, List<OrderItemXML>> getAll(int id) throws JAXBException, IOException {
        return dao.getAll(id);
    }

    @Override
    public void delete(int orderId) {
        dao.delete(orderId);
    }

    @Override
    public void writeToXML(OrderXML order) {
        dao.save(order);
    }

}
