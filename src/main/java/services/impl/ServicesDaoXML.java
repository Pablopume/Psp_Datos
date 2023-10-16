package services.impl;

import dao.OrderDAOXML;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
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
    public List<OrderXML> getAll() throws IOException, JAXBException {
        return dao.getAll();
    }

    @Override
    public List<OrderItemXML> getAll(int id) throws JAXBException, IOException {
        return dao.getAll(id);
    }

}
