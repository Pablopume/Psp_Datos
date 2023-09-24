package ui.pantallas.orders.editorder;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.orders.deleteorders.DeleteOrderViewModel;

import java.time.LocalDateTime;

public class EditOrderController extends BasePantallaController {
    @FXML
    public TableView<Order> customersTable;
    @FXML
    public TableColumn<Order, Integer> idOrder;
    @FXML
    public TableColumn<Order, LocalDateTime> orderDate;

    @FXML
    public TableColumn<Order, Integer> customerId;
    @FXML
    public TableColumn<Order, String > tableId;
    public TextField idTxtField;
    public TextField custIdField;
    public TextField tableFIeld;
    public DatePicker dateField;
    @Inject
    EditOrderViewModel editOrderViewModel;

    public void initialize() {
        idOrder.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        tableId.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        customersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                Order selectedOrder = customersTable.getSelectionModel().getSelectedItem();


                idTxtField.setText(Integer.toString(selectedOrder.getId()));
                custIdField.setText(Integer.toString(selectedOrder.getCustomer_id()));
                tableFIeld.setText(Integer.toString(selectedOrder.getTable_id()));
                dateField.setValue(selectedOrder.getDate().toLocalDate());
            }
        });

        editOrderViewModel.getState().addListener((observableValue, oldValue, newValue) -> {

                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError());
                    }
                    if (newValue.getListOrders() != null) {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(newValue.getListOrders());
                    }

                }

        );
        editOrderViewModel.voidState();

    }
    @Override
    public void principalCargado() {
        editOrderViewModel.loadState();
    }

    public void addeOrder(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order added");
        alert.setHeaderText(null);
        alert.setContentText("The order has been added.");
        alert.showAndWait();

    }

    public void editOrder(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order edited");
        alert.setHeaderText(null);
        alert.setContentText("Order edited correctly.");
        alert.showAndWait();
    }
}
