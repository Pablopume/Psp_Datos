package ui.pantallas.customers.showcustomers;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;

public class ShowCustomersController extends BasePantallaController {
    @FXML
    private TableColumn<Customer, Integer> idCustomerColumn;
    @FXML
    private TableColumn<Customer, String> nameCustomerColumn;
    @FXML
    private TableColumn<Customer, String> surnameCustomerColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, LocalDate> dateOfBirthdayColumn;
    @FXML
    private TableView<Customer> customersTable;
    @Inject
    private ShowCustomersViewModel custViewModel;



    public void initialize() {
        idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        surnameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        custViewModel.getState().addListener((observableValue, oldValue, newValue) -> {

                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError());
                    }
                    if (newValue.getListCustomers() != null) {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(newValue.getListCustomers());
                    }

                }

        );
        custViewModel.voidState();

    }

    @Override
    public void principalCargado() {
        custViewModel.loadState();
    }
}
