package ui.pantallas.customers.showcustomers;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.Customer;

import java.util.List;


@Data

public class ShowCustomersState {

    private final List<Customer> listCustomers;
    private final String error;


}
