package ui.pantallas.customers.editcustomers;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import ui.pantallas.common.BasePantallaController;

public class EditCustomersControllers extends BasePantallaController {
    @FXML
    public TextField idTxtField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;

    @FXML
    public TextField mailField;
    @FXML
    public TextField phoneField;
    @FXML
    public DatePicker dobField;
    @FXML
    public TableView customersTable;
    @FXML
    public TableColumn idCustomerColumn;
    @FXML
    public TableColumn nameCustomerColumn;
    @FXML
    public TableColumn surnameCustomerColumn;
    @FXML
    public TableColumn emailColumn;
    @FXML
    public TableColumn phoneColumn;
    @FXML
    public TableColumn dateOfBirthdayColumn;

    @Inject
    private EditCustomerViewModel editCustomerViewModel;

    public void handleCustomerSelection() {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            idTxtField.setText(Integer.toString(selectedCustomer.getId()));
            nameField.setText(selectedCustomer.getFirst_name());
            surnameField.setText(selectedCustomer.getLast_name());
            mailField.setText(selectedCustomer.getEmail());
            phoneField.setText(selectedCustomer.getPhone());
            dobField.setValue(selectedCustomer.getDob());
        }
    }


    public void initialize() {
        idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        surnameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        customersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleCustomerSelection();
        });
        editCustomerViewModel.getState().addListener((observableValue, oldValue, newValue) -> {

                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError());
                    }
                    if (newValue.getListCustomers() != null) {

                        customersTable.getItems().clear();
                        customersTable.getItems().setAll(newValue.getListCustomers());


                    }

                }

        );
        editCustomerViewModel.voidState();

    }

    @Override
    public void principalCargado() {
        editCustomerViewModel.loadState();
    }

    public void editClient(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User updated");
        alert.setHeaderText(null);
        alert.setContentText("The user has been updated correctly.");
        alert.showAndWait();
    }
}
