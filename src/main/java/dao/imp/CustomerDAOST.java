package dao.imp;

import dao.CustomerDAO;
import model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOST implements CustomerDAO {


    @Override
    public List<Customer> getAll() {
        List<Customer> listCust=new ArrayList<>();
        listCust.add(new Customer(1, "Izan","Russel","rusa@gmail.com","84990583490", LocalDate.of(2001,12,31)));
        listCust.add(new Customer(2, "Asturian","Hasbulla","rucceo@gmail.com","58994490", LocalDate.of(2004,11,27)));
        listCust.add(new Customer(3, "Augustus","Gloop","glabenaa@gmail.com","390583490", LocalDate.of(2001,12,31)));
        return listCust;
    }
}
