package ui.pantallas.customers.showcustomers;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Customer;
import services.ServicesCustomer;

import java.util.ArrayList;
import java.util.List;

public class ShowCustomersViewModel {
    private final ServicesCustomer services;
    private final ObjectProperty<ShowCustomersState> state;

    @Inject
    public ShowCustomersViewModel(ServicesCustomer services) {
        this.state = new SimpleObjectProperty<>(new ShowCustomersState(new ArrayList<>(), null));
        this.services = services;

    }
public void  voidState(){state.set(new ShowCustomersState(null,null));}
public ReadOnlyObjectProperty<ShowCustomersState> getState(){return state;}
    public void loadState() {
        List<Customer> listCust = services.getAll();
        if (listCust.isEmpty()) {
            state.set(new ShowCustomersState(null,"There are no customers"));


        }else {
           state.set(new ShowCustomersState(listCust,null));
        }
    }


}
