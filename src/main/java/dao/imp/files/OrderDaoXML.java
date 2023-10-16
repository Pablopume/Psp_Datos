package dao.imp.files;

import configuration.ConfigXML;
import dao.OrderDAOXML;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Order;
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
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderDaoXML implements OrderDAOXML {
    public List<OrderXML> getAll() throws IOException, JAXBException {
        Path xmlFile = Paths.get(ConfigXML.getInstance().getProperty("xmlPath"));
        JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        OrdersXML orders = (OrdersXML) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
        return orders.getOrderList();
    }

    public List<OrderItemXML> getAll(int id) throws JAXBException, IOException {
        List<OrderXML> allOrders = getAll();
        Optional<OrderXML> matchingOrder = allOrders.stream()
                .filter(order -> order.getId() == id)
                .findFirst();

        if (matchingOrder.isPresent()) {
            return matchingOrder.get().getOrderItem();
        } else {
            return new ArrayList<>();
        }
    }

    public void delete(int orderId) {
        try {
            Path xmlFile = Paths.get(ConfigXML.getInstance().getProperty("xmlPath"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile.toFile());
            Element root = doc.getDocumentElement();
            NodeList orderList = root.getElementsByTagName("order");
            List<Element> ordersToDelete = new ArrayList<>();
            for (int i = 0; i < orderList.getLength(); i++) {
                Element orderElement = (Element) orderList.item(i);
                Element idElement = (Element) orderElement.getElementsByTagName("id").item(0);
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

            System.out.println("Pedido con ID " + orderId + " eliminado con éxito.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }


    public void save(OrderXML order) {
        try {
            Path xmlFile = Paths.get(ConfigXML.getInstance().getProperty("xmlPath"));
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

            // Escribe el contenido combinado en el archivo XML
            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            jaxbMarshaller.marshal(ordersXML, fileWriter);
            fileWriter.close();
        } catch (JAXBException | java.io.IOException e) {
            e.printStackTrace();
        }
    }
}



