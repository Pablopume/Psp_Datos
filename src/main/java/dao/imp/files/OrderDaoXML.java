package dao.imp.files;

import configuration.ConfigXML;
import dao.OrderDAOXML;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.xml.OrderXML;
import model.xml.OrdersXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OrderDaoXML implements OrderDAOXML {
    public List<OrderXML> getAll() throws IOException, JAXBException {
        Path xmlFile= Paths.get(ConfigXML.getInstance().getProperty("xmlPath"));
        JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        OrdersXML orders = (OrdersXML) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
        return orders.getOrderList();
    }


}
