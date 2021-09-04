package carsharing.dao;

import carsharing.model.Customer;

import java.util.Collection;

public interface CustomerDao {
    void addCustomer(String name);

    Collection<Customer> getAllCustomers();
}
