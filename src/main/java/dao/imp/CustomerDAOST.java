package dao.imp;

import common.Constants;
import dao.CustomerDAO;
import model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOST implements CustomerDAO {
    private final List<Customer> listCust;

    public CustomerDAOST() {
        this.listCust = new ArrayList<>();
        this.listCust.add(new Customer(1, Constants.IZAN, Constants.RUSSEL, Constants.MAIL, Constants.NUMBER, LocalDate.of(2001, 12, 31)));
        this.listCust.add(new Customer(2, Constants.ASTURIAN, Constants.HASBULLA, Constants.MAIL1, Constants.NUMBER1, LocalDate.of(2004, 11, 27)));
        this.listCust.add(new Customer(3, Constants.AUGUSTUS, Constants.GLOOP, Constants.MAIL2, Constants.NUMBER2, LocalDate.of(2001, 12, 31)));
    }

    @Override
    public List<Customer> getAll() {
        return listCust;
    }
}
