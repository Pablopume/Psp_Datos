package ui.pantallas.customers.addcustomer;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;

public class AddCustomersController extends BasePantallaController {
    @FXML
    public TextField nameField;
    @FXML
    public TextField idTxtField;
    @FXML
    public TextField phoneField;
    @FXML
    public TextField surnameField;
    public TextField mailField;
    public DatePicker dobField;
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
    private AddCustomerViewModel addCustomerViewModel;

    public void initialize() {
        idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        surnameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        addCustomerViewModel.getState().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError());
                    }
                    if (newValue.getListCustomers() != null) {
                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(newValue.getListCustomers());
                    }
                }

        );
        addCustomerViewModel.voidState();
    }

    @Override
    public void principalCargado() {
        addCustomerViewModel.loadState();
    }


    public void addClient(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuario creado");
        alert.setHeaderText(null);
        alert.setContentText("El usuario se ha creado correctamente.");
        alert.showAndWait();
    }
}
