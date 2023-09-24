package ui.pantallas.orders.addorder;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Order;
import services.ServicesOrder;
import ui.pantallas.orders.deleteorders.DeleteOrderState;

import java.util.ArrayList;
import java.util.List;

public class AddOrderViewModel {
    private final ServicesOrder services;
    private final ObjectProperty<AddOrderState> state;

    @Inject
    public AddOrderViewModel(ServicesOrder services) {
        this.state = new SimpleObjectProperty<>(new AddOrderState(new ArrayList<>(), null));
        this.services = services;

    }

    public void voidState() {
        state.set(new AddOrderState(null, null));
    }
    public ReadOnlyObjectProperty<AddOrderState> getState(){return state;}

    public void loadState() {
        List<Order> listOrd = services.getAll();
        if (listOrd.isEmpty()) {
            state.set(new AddOrderState(null,"There are no orders"));


        }else {
            state.set(new AddOrderState(listOrd,null));
        }
    }
}
