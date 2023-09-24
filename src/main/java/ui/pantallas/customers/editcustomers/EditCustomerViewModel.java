package ui.pantallas.customers.editcustomers;

import jakarta.inject.Inject;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Customer;
import services.ServicesCustomer;
import ui.pantallas.customers.deletecustomer.DeleteCustomerState;

import java.util.ArrayList;
import java.util.List;

public class EditCustomerViewModel {
    private final ServicesCustomer services;
    private final ObjectProperty<EditCustomerState> state;
    @Inject
    public EditCustomerViewModel(ServicesCustomer services) {
        this.services = services;
        this.state =  new SimpleObjectProperty<>(new EditCustomerState(new ArrayList<>(), null));;
    }

    public ReadOnlyObjectProperty<EditCustomerState> getState() {
        return state;
    }

    public void  voidState(){state.set(new EditCustomerState(null,null));}

    public void loadState() {
        List<Customer> listCust = services.getAll();
        if (listCust.isEmpty()) {
            state.set(new EditCustomerState(null,"There are no customers"));


        }else {
            state.set(new EditCustomerState(listCust,null));
        }
    }
}
