package ui.pantallas.orders.editorder;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Order;
import services.ServicesOrder;
import ui.pantallas.orders.addorder.AddOrderState;
import ui.pantallas.orders.deleteorders.DeleteOrderState;

import java.util.ArrayList;
import java.util.List;

public class EditOrderViewModel {
    private final ServicesOrder services;
    private final ObjectProperty<EditOrderState> state;

    @Inject
    public EditOrderViewModel(ServicesOrder services) {
        this.state = new SimpleObjectProperty<>(new EditOrderState(new ArrayList<>(), null));
        this.services = services;

    }

    public void voidState() {
        state.set(new EditOrderState(null, null));
    }

    public ReadOnlyObjectProperty<EditOrderState> getState(){return state;}

    public void loadState() {
        List<Order> listOrd = services.getAll();
        if (listOrd.isEmpty()) {
            state.set(new EditOrderState(null,"There are no orders"));


        }else {
            state.set(new EditOrderState(listOrd,null));
        }
    }
}
