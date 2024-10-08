package ui.screens.customers.deletecustomer;

import common.Constants;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import model.Customer;
import services.ServicesCustomer;
import services.ServicesOrder;

import java.util.ArrayList;
import java.util.List;

public class DeleteCustomerViewModel {
    @Getter
    private final ServicesCustomer services;
    @Getter
    private final ServicesOrder serviceOrder;
    private final ObjectProperty<DeleteCustomerState> state;

    @Inject
    public DeleteCustomerViewModel(ServicesCustomer services, ServicesOrder serviceOrder) {
        this.services = services;
        this.serviceOrder = serviceOrder;
        this.state = new SimpleObjectProperty<>(new DeleteCustomerState(new ArrayList<>(), null));
        ;
    }

    public ReadOnlyObjectProperty<DeleteCustomerState> getState() {
        return state;
    }

    public void voidState() {
        state.set(new DeleteCustomerState(null, null));
    }

    public void loadState() {
        List<Customer> listCust = services.getAll().get();
        if (listCust.isEmpty()) {
            state.set(new DeleteCustomerState(null, Constants.THERE_ARE_NO_CUSTOMERS2));
        } else {
            state.set(new DeleteCustomerState(listCust, null));
        }
    }
}
