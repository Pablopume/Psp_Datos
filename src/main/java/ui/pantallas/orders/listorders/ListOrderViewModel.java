package ui.pantallas.orders.listorders;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Customer;
import model.Order;
import services.ServicesCustomer;
import services.ServicesOrder;
import ui.pantallas.customers.showcustomers.ShowCustomersState;

import java.util.ArrayList;
import java.util.List;

public class ListOrderViewModel {
    private final ServicesOrder services;
    private final ObjectProperty<ListOrderState> state;

    @Inject
    public ListOrderViewModel(ServicesOrder services) {
        this.state = new SimpleObjectProperty<>(new ListOrderState(new ArrayList<>(), null));
        this.services=services;

    }
    public void  voidState(){state.set(new ListOrderState(null,null));}

    public ReadOnlyObjectProperty<ListOrderState> getState(){return state;}

    public void loadState() {
        List<Order> listOrd = services.getAll();
        if (listOrd.isEmpty()) {
            state.set(new ListOrderState(null,"There are no orders"));


        }else {
            state.set(new ListOrderState(listOrd,null));
        }
    }

}
