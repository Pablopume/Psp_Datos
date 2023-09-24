package ui.screens.orders.editorder;

import common.Constants;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import ui.screens.common.BaseScreenController;

import java.time.LocalDateTime;

public class EditOrderController extends BaseScreenController {
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
        idOrder.setCellValueFactory(new PropertyValueFactory<>(Constants.ID));
        orderDate.setCellValueFactory(new PropertyValueFactory<>(Constants.DATE));
        customerId.setCellValueFactory(new PropertyValueFactory<>(Constants.CUSTOMER_ID));
        tableId.setCellValueFactory(new PropertyValueFactory<>(Constants.TABLE_ID));
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
    public void principalLoaded() {
        editOrderViewModel.loadState();
    }



    public void editOrder(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Constants.ORDER_EDITED);
        alert.setHeaderText(null);
        alert.setContentText(Constants.ORDER_EDITED_CORRECTLY);
        alert.showAndWait();
    }
}
