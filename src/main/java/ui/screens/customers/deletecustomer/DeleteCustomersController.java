package ui.screens.customers.deletecustomer;

import common.Constants;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import ui.screens.common.BaseScreenController;

import java.time.LocalDate;

public class DeleteCustomersController extends BaseScreenController {
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
        idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>(Constants.ID2));
        nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>(Constants.FIRST_NAME2));
        surnameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>(Constants.LAST_NAME2));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>(Constants.EMAIL2));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>(Constants.PHONE2));
        dateOfBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>(Constants.DOB2));
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
    public void principalLoaded() {
        deleteCustomerViewModel.loadState();
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Constants.USER_DELETED);
        alert.setHeaderText(null);
        alert.setContentText(Constants.THE_USER_HAS_BEEN_DELETED);
        alert.showAndWait();

    }
}
