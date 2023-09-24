package ui.pantallas.orders.listorders;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDateTime;

public class ListOrderController extends BasePantallaController {
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
    ListOrderViewModel listOrderViewModel;

    public void initialize() {
        idOrder.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        tableId.setCellValueFactory(new PropertyValueFactory<>("table_id"));


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

    @Override
    public void principalCargado() {
        listOrderViewModel.loadState();
    }


}
