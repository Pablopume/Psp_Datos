package dao.imp.files;

import common.Constants;
import configuration.ConfigXML;
import dao.OrderDAOXML;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.errors.OrderError;
import model.xml.OrderItemXML;
import model.xml.OrderXML;
import model.xml.OrdersXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vavr.control.Either;

public class OrderDaoXML implements OrderDAOXML {


    public Either<OrderError, List<OrderXML>> getAll() {
        Either<OrderError, List<OrderXML>> resultado;
        try {
            Path xmlFile = Paths.get(ConfigXML.getInstance().getProperty(Constants.XML_PATH));
            JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            OrdersXML orders = (OrdersXML) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
            resultado = Either.right(orders.getOrderList());
        } catch (IOException | JAXBException e) {
            resultado = Either.left(new OrderError(Constants.ERROR_WHILE_RETRIEVING_ORDERS));
        }
        return resultado;
    }

    public Either<OrderError, List<OrderItemXML>> getAll(int id) {
        Either<OrderError, List<OrderItemXML>> resultado;
        try {
            List<OrderXML> allOrders = getAll().getOrElse(new ArrayList<>());
            Optional<OrderXML> matchingOrder = allOrders.stream()
                    .filter(order -> order.getId() == id)
                    .findFirst();

            resultado = matchingOrder.<Either<OrderError, List<OrderItemXML>>>map(orderXML -> Either.right(orderXML.getOrderItem())).orElseGet(() -> Either.right(new ArrayList<>()));
        } catch (Exception e) {
            resultado = Either.left(new OrderError(Constants.ERROR_WHILE_RETRIEVING_ORDERS));
        }
        return resultado;

    }

    public void delete(int orderId) {
        try {
            Path xmlFile = Paths.get(ConfigXML.getInstance().getProperty(Constants.XML_PATH));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile.toFile());
            Element root = doc.getDocumentElement();
            NodeList orderList = root.getElementsByTagName(Constants.ORDER);
            List<Element> ordersToDelete = new ArrayList<>();
            for (int i = 0; i < orderList.getLength(); i++) {
                Element orderElement = (Element) orderList.item(i);
                Element idElement = (Element) orderElement.getElementsByTagName(Constants.ID).item(0);
                int id = Integer.parseInt(idElement.getTextContent());

                if (id == orderId) {
                    ordersToDelete.add(orderElement);
                }
            }
            for (Element orderElement : ordersToDelete) {
                root.removeChild(orderElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFile.toUri()));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }


    public void save(OrderXML order) {
        try {
            Path xmlFile = Paths.get(ConfigXML.getInstance().getProperty(Constants.XML_PATH));
            File file = xmlFile.toFile();
            OrdersXML ordersXML = new OrdersXML();
            boolean fileExists = file.exists();
            if (fileExists) {
                JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXML.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                ordersXML = (OrdersXML) unmarshaller.unmarshal(file);
            }
            ordersXML.getOrderList().add(order);
            JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXML.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            jaxbMarshaller.marshal(ordersXML, fileWriter);
            fileWriter.close();
        } catch (JAXBException | java.io.IOException e) {
            e.printStackTrace();
        }
    }
}



