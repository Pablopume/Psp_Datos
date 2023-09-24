package ui.pantallas.customers.addcustomer;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Customer;
import services.ServicesCustomer;
import ui.pantallas.customers.showcustomers.ShowCustomersState;

import java.util.ArrayList;
import java.util.List;

public class AddCustomerViewModel {
    private final ServicesCustomer services;
    private final ObjectProperty<AddCustomerState> state;
    @Inject
    public AddCustomerViewModel(ServicesCustomer services) {
        this.services = services;
        this.state =  new SimpleObjectProperty<>(new AddCustomerState(new ArrayList<>(), null));;
    }

    public ReadOnlyObjectProperty<AddCustomerState> getState(){return state;}
    public void  voidState(){state.set(new AddCustomerState(null,null));}

    public void loadState() {
        List<Customer> listCust = services.getAll();
        if (listCust.isEmpty()) {
            state.set(new AddCustomerState(null,"There are no customers"));


        }else {
            state.set(new AddCustomerState(listCust,null));
        }
    }
}
