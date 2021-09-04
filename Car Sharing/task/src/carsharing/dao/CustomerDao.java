package carsharing.dao;

import carsharing.model.Customer;

import java.util.List;

public interface CustomerDao {
    void addCustomer(String name);

    List<Customer> getAllCustomers();
}
