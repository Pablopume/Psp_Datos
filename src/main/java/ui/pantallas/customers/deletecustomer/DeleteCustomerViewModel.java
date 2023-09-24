package ui.pantallas.customers.deletecustomer;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Customer;
import services.ServicesCustomer;

import java.util.ArrayList;
import java.util.List;

public class DeleteCustomerViewModel {

    private final ServicesCustomer services;
    private final ObjectProperty<DeleteCustomerState> state;

    @Inject
    public DeleteCustomerViewModel(ServicesCustomer services) {
        this.services = services;
        this.state =  new SimpleObjectProperty<>(new DeleteCustomerState(new ArrayList<>(), null));;
    }
    public ReadOnlyObjectProperty<DeleteCustomerState> getState(){return state;}
    public void  voidState(){state.set(new DeleteCustomerState(null,null));}
    public void loadState() {
        List<Customer> listCust = services.getAll();
        if (listCust.isEmpty()) {
            state.set(new DeleteCustomerState(null,"There are no customers"));


        }else {
            state.set(new DeleteCustomerState(listCust,null));
        }
    }
}
