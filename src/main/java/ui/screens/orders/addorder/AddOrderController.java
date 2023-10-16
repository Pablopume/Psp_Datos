package ui.screens.orders.addorder;

import common.Constants;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import ui.screens.common.BaseScreenController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public ComboBox<String> orderId;
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox<String> idCustomer;
    @FXML
    public ComboBox<String> table_id;


    @Inject
    AddOrderViewModel addOrderViewModel;

    public void initialize() {
        idOrder.setCellValueFactory(new PropertyValueFactory<>(Constants.ID));
        orderDate.setCellValueFactory(new PropertyValueFactory<>(Constants.DATE));
        customerId.setCellValueFactory(new PropertyValueFactory<>(Constants.CUSTOMER_ID));
        tableId.setCellValueFactory(new PropertyValueFactory<>(Constants.TABLE_ID));


        addOrderViewModel.getState().addListener((observableValue, oldValue, newValue) -> {

                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError());
                    }
                    if (newValue.getListOrders() != null) {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(newValue.getListOrders());
                    }
                }
        );
        addOrderViewModel.voidState();

    }

    @Override
    public void principalLoaded() {
        addOrderViewModel.loadState();
    }

    public void addOrder(ActionEvent actionEvent) {
        int selectedOrderId = Integer.parseInt(orderId.getValue());
        int selectedCustomerId = Integer.parseInt(idCustomer.getValue());
        int selectedTableId = Integer.parseInt(table_id.getValue());
        addOrderViewModel.getServices().writeToFile(addOrderViewModel.getServices().createOrder(selectedOrderId, LocalDateTime.now(), selectedCustomerId, selectedTableId));
        customersTable.getItems().add(addOrderViewModel.getServices().createOrder(selectedOrderId, LocalDateTime.now(), selectedCustomerId, selectedTableId));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Constants.ORDER_ADDED);
        alert.setHeaderText(null);
        alert.setContentText(Constants.THE_ORDER_HAS_BEEN_ADDED);
        alert.showAndWait();

    }
}
