package ui.pantallas.orders.deleteorders;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.orders.listorders.ListOrderViewModel;

import java.time.LocalDateTime;

public class DeleteOrderController extends BasePantallaController {
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
    DeleteOrderViewModel deleteOrderViewModel;

    public void initialize() {
        idOrder.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        tableId.setCellValueFactory(new PropertyValueFactory<>("table_id"));


        deleteOrderViewModel.getState().addListener((observableValue, oldValue, newValue) -> {

                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError());
                    }
                    if (newValue.getListOrders() != null) {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(newValue.getListOrders());
                    }

                }

        );
        deleteOrderViewModel.voidState();

    }
    @Override
    public void principalCargado() {
        deleteOrderViewModel.loadState();
    }

    public void deleteOrder(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order deleted");
        alert.setHeaderText(null);
        alert.setContentText("The order has been deleted.");
        alert.showAndWait();

    }

}
