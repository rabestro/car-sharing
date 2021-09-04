package carsharing.dao.impl;

import carsharing.dao.CustomerDao;
import carsharing.dao.Repository;
import carsharing.model.Customer;
import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import static java.lang.System.Logger.Level.ERROR;

@AllArgsConstructor
public class CustomerDaoImpl implements CustomerDao {
    private static final System.Logger LOGGER = System.getLogger("");

    private static final String SQL_INSERT_CUSTOMER = "INSERT INTO customer (name) VALUES (?)";
    private static final String SQL_UPDATE_CAR = "UPDATE customer SET rented_car_id = ? WHERE id = ?";

    private static final Function<ResultSet, Customer> CUSTOMER_BUILDER = rs -> {
        try {
            var customer = new Customer(rs.getInt("id"), rs.getString("name"));
            customer.setCarId(rs.getInt("rented_car_id"));
            return customer;
        } catch (SQLException e) {
            LOGGER.log(ERROR, e::getMessage);
        }
        return Customer.EMPTY;
    };

    private final Repository repository;

    @Override
    public void addCustomer(String name) {
        repository.update(SQL_INSERT_CUSTOMER, name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return repository.select("select * from customer", CUSTOMER_BUILDER);
    }

    @Override
    public void update(Customer customer) {
        repository.update(SQL_UPDATE_CAR, customer.getCarId(), customer.getId());
    }
}
