package carsharing.dao.impl;

import carsharing.dao.CustomerDao;
import carsharing.dao.Repository;
import carsharing.model.Customer;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class CustomerDaoImpl implements CustomerDao {
    private static final System.Logger LOGGER = System.getLogger("");

    private static final String SQL_INSERT_CUSTOMER = "INSERT INTO customer (name) VALUES (?)";

    private final Repository repository;

    @Override
    public void addCustomer(String name) {
        repository.insert(SQL_INSERT_CUSTOMER, name);
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        return Collections.emptyList();
    }
}
