package ui.screens.orders.addorder;

import common.Constants;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.xml.OrderItemXML;
import model.xml.OrderXML;
import ui.screens.common.BaseScreenController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddOrderController extends BaseScreenController {
    @FXML
    public TableView<Order> customersTable;
    @FXML
    public TableColumn<Order, Integer> idOrder;
    @FXML
    public TableColumn<Order, LocalDateTime> orderDate;

    @FXML
    public TableColumn<Order, Integer> customerId;
    @FXML
    public TableColumn<Order, Integer> tableId;

    @FXML
    public ComboBox<String> idCustomer;
    @FXML
    public ComboBox<String> table_id;
    public TableColumn<OrderItemXML, String> menuItem;
    public TableView<OrderItemXML> ordersXMLTable;
    public TableColumn<OrderItemXML, Integer> quantity;
    public ComboBox<String> menuItems;
    public TextField quantityItems;


    @Inject
    AddOrderViewModel addOrderViewModel;

    public void initialize() {
        menuItem.setCellValueFactory(new PropertyValueFactory<>("menuItem"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        addOrderViewModel.voidState();

    }

    @Override
    public void principalLoaded() {
        addOrderViewModel.loadState();
    }

    public void addOrder(ActionEvent actionEvent) {
        int selectedCustomerId = Integer.parseInt(idCustomer.getValue());
        int selectedTableId = Integer.parseInt(table_id.getValue());
        addOrderViewModel.getServicesDaoXML().writeToXML(new OrderXML(addOrderViewModel.getServices().createOrder(LocalDateTime.now(),selectedCustomerId,selectedTableId).get().getId(),ordersXMLTable.getItems()));
        addOrderViewModel.getServices().writeToFile(addOrderViewModel.getServices().createOrder( LocalDateTime.now(), selectedCustomerId, selectedTableId).get());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Constants.ORDER_ADDED);
        alert.setHeaderText(null);
        alert.setContentText(Constants.THE_ORDER_HAS_BEEN_ADDED);
        alert.showAndWait();

    }

    public void addItem(ActionEvent actionEvent) {
        ObservableList<OrderItemXML> orderItemXMLS= ordersXMLTable.getItems();
        orderItemXMLS.add(new OrderItemXML(menuItems.getValue(),Integer.parseInt(quantityItems.getText())));
    }

    public void removeOrder(ActionEvent actionEvent) {

        ObservableList<OrderItemXML> orderItemXMLS= ordersXMLTable.getItems();
        OrderItemXML selectedOrder = ordersXMLTable.getSelectionModel().getSelectedItem();
        orderItemXMLS.remove(selectedOrder);
    }
}
