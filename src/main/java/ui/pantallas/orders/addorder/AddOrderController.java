package ui.pantallas.orders.addorder;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.orders.deleteorders.DeleteOrderViewModel;

import java.time.LocalDateTime;

public class AddOrderController extends BasePantallaController {
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
    @Inject
    AddOrderViewModel addOrderViewModel;

    public void initialize() {
        idOrder.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        tableId.setCellValueFactory(new PropertyValueFactory<>("table_id"));


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
    public void principalCargado() {
        addOrderViewModel.loadState();
    }

    public void addeOrder(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order added");
        alert.setHeaderText(null);
        alert.setContentText("The order has been added.");
        alert.showAndWait();

    }
}
