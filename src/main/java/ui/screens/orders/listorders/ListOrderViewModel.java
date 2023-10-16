package ui.screens.orders.listorders;

import common.Constants;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;
import lombok.Getter;
import model.Order;
import services.ServicesOrder;

import java.util.ArrayList;
import java.util.List;
@Data
public class ListOrderViewModel {

    private final ServicesOrder services;

    private final ObjectProperty<ListOrderState> state;

    @Inject
    public ListOrderViewModel(ServicesOrder services) {
        this.state = new SimpleObjectProperty<>(new ListOrderState(new ArrayList<>(), null));
        this.services = services;

    }

    public void voidState() {
        state.set(new ListOrderState(null, null));
    }

    public ReadOnlyObjectProperty<ListOrderState> getState() {
        return state;
    }

    public void loadState() {
        List<Order> listOrd = services.getAll();
        if (listOrd.isEmpty()) {
            state.set(new ListOrderState(null, Constants.THERE_ARE_NO_ORDERS));


        } else {
            state.set(new ListOrderState(listOrd, null));
        }
    }

}
