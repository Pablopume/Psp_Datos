package ui.screens.orders.listorders;

import common.Constants;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import ui.screens.common.BaseScreenController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ListOrderController extends BaseScreenController {
    @FXML
    public TableView<Order> customersTable;
    @FXML
    public TableColumn<Order, Integer> idOrder;
    @FXML
    public TableColumn<Order, LocalDateTime> orderDate;

    @FXML
    public TableColumn<Order, Integer> customerId;
    @FXML
    public TableColumn<Order, String> tableId;
    @FXML
    public TextField customerTextField;
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox<String> comboBox;
    @Inject
    ListOrderViewModel listOrderViewModel;

    public void initialize() {
        datePicker.setVisible(false);
        customerTextField.setVisible(false);
        idOrder.setCellValueFactory(new PropertyValueFactory<>(Constants.ID));
        orderDate.setCellValueFactory(new PropertyValueFactory<>(Constants.DATE));
        customerId.setCellValueFactory(new PropertyValueFactory<>(Constants.CUSTOMER_ID));
        tableId.setCellValueFactory(new PropertyValueFactory<>(Constants.TABLE_ID));
        filterOptions();
        customerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == null || newValue.trim().isEmpty()) {
                        customersTable.getItems().setAll(listOrderViewModel.getServices().getAll());
                    } else {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(listOrderViewModel.getServices().filteredList(Integer.parseInt(customerTextField.getText())));
                    }
                }
        );
        datePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue == null) {
                        customersTable.getItems().setAll(listOrderViewModel.getServices().getAll());
                    } else {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(listOrderViewModel.getServices().filteredListDate(datePicker.getValue()));
                    }
                }

        );

        listOrderViewModel.getState().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError());
                    }
                    if (newValue.getListOrders() != null) {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(newValue.getListOrders());
                    }
                }
        );
        listOrderViewModel.voidState();

    }

    private void filterOptions() {
        comboBox.setOnAction(event -> {
            String selectedItem = comboBox.getSelectionModel().getSelectedItem();
            if ("Customer ID".equals(selectedItem)) {
                customerTextField.setVisible(true);
                datePicker.setVisible(false);
            } else if ("Date".equals(selectedItem)) {
                customerTextField.setVisible(false);
                datePicker.setVisible(true);
            }
        });
    }

    @Override
    public void principalLoaded() {
        listOrderViewModel.loadState();
    }


}
