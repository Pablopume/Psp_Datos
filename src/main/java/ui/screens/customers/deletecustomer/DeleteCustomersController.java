package ui.screens.customers.deletecustomer;

import common.Constants;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import ui.screens.common.BaseScreenController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

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
        SelectionModel<Customer> selectionModel = customersTable.getSelectionModel();
        Customer selectedCustomer = selectionModel.getSelectedItem();
        if (!deleteCustomerViewModel.getServices().orderContained(selectedCustomer.getId(), deleteCustomerViewModel.getServiceOrder().getAll().get()).get()) {

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm delete");
            confirmationAlert.setHeaderText("Delete customer");
            confirmationAlert.setContentText("Are you sure you want to delete this Customer?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteCustomerViewModel.getServiceOrder().getAll();
                deleteCustomerViewModel.getServices().deleteLineById(selectedCustomer.getId());
                ObservableList<Customer> customers = customersTable.getItems();
                customers.remove(selectedCustomer);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Customer deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The Customer has been deleted");
                successAlert.showAndWait();
            }
        } else if (deleteCustomerViewModel.getServices().orderContained(selectedCustomer.getId(), deleteCustomerViewModel.getServiceOrder().getAll().get()).get()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("You can't delete");
            alert.setHeaderText(null);
            alert.setContentText("There are orders created in that customer, do you want to delete them?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteCustomerViewModel.getServiceOrder().deleteOrders(deleteCustomerViewModel.getServiceOrder().getAll().get(), selectedCustomer.getId());
                deleteCustomerViewModel.getServices().deleteLineById(selectedCustomer.getId());
                ObservableList<Customer> customers = customersTable.getItems();
                customers.remove(selectedCustomer);
            }
        } else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete cancelled");
            alert.setHeaderText(null);
            alert.setContentText("You cancelled the delete");
            alert.show();
        }
    }

}

