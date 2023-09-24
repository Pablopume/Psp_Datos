package ui.pantallas.customers.deletecustomer;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;

public class DeleteCustomersController extends BasePantallaController {
    @FXML
    public TableView<Customer> customersTable;
    @FXML
    public TableColumn<Customer, Integer> idCustomerColumn;
    @FXML
    public TableColumn<Customer, String> nameCustomerColumn;
    @FXML
    public TableColumn<Customer, String> surnameCustomerColumn;
    @FXML
    public TableColumn<Customer, String> emailColumn;
    @FXML
    public TableColumn<Customer, String> phoneColumn;
    @FXML
    public TableColumn<Customer, LocalDate> dateOfBirthdayColumn;
    @FXML
    public Button buttonCostumer;
    public TableView ordersCustomer;
    public TableColumn product;
    public TableColumn idProduct;
    public TableColumn price;

    @Inject
    private DeleteCustomerViewModel deleteCustomerViewModel;

    public void initialize() {
        idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        surnameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        deleteCustomerViewModel.getState().addListener((observableValue, oldValue, newValue) -> {

                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError());
                    }
                    if (newValue.getListCustomers() != null) {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(newValue.getListCustomers());
                    }

                }

        );
        deleteCustomerViewModel.voidState();

    }

    @Override
    public void principalCargado() {
        deleteCustomerViewModel.loadState();
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User deleted");
        alert.setHeaderText(null);
        alert.setContentText("The user has been deleted.");
        alert.showAndWait();

    }
}
