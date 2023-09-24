package ui.pantallas.orders.deleteorders;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Order;
import services.ServicesOrder;
import ui.pantallas.orders.listorders.ListOrderState;

import java.util.ArrayList;
import java.util.List;

public class DeleteOrderViewModel {

    private final ServicesOrder services;
    private final ObjectProperty<DeleteOrderState> state;



    @Inject
    public DeleteOrderViewModel(ServicesOrder services) {
        this.state = new SimpleObjectProperty<>(new DeleteOrderState(new ArrayList<>(), null));
        this.services = services;

    }
    public void voidState() {
        state.set(new DeleteOrderState(null, null));
    }
    public ReadOnlyObjectProperty<DeleteOrderState> getState(){return state;}

    public void loadState() {
        List<Order> listOrd = services.getAll();
        if (listOrd.isEmpty()) {
            state.set(new DeleteOrderState(null,"There are no orders"));


        }else {
            state.set(new DeleteOrderState(listOrd,null));
        }
    }
}
