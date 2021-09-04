package carsharing.dao.impl;

import carsharing.dao.CustomerDao;
import carsharing.dao.Repository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerDaoImpl implements CustomerDao {
    private static final System.Logger LOGGER = System.getLogger("");

    private static final String SQL_INSERT_CUSTOMER = "INSERT INTO customer (name) VALUES (?)";

    private final Repository repository;

    @Override
    public void addCustomer(String name) {
        repository.insert(SQL_INSERT_CUSTOMER, name);
    }
}
