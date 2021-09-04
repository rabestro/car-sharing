package carsharing.dao.impl;

import carsharing.dao.CustomerDao;
import carsharing.dao.Repository;
import carsharing.model.Customer;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static java.lang.System.Logger.Level.ERROR;

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
    public List<Customer> getAllCustomers() {
        return repository.getAll(
                "select id, name, rented_car_id from customer",
                rs -> {
                    try {
                        return new Customer(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getInt(3));
                    } catch (SQLException e) {
                        LOGGER.log(ERROR, e::getMessage);
                    }
                    return Customer.EMPTY;
                });
    }
}
