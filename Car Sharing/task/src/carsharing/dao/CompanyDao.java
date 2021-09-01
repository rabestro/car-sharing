package carsharing.dao;

import carsharing.model.Company;

import java.util.Collection;

public interface CompanyDao {
    Collection<Company> getAllCompanies();

    void addCompany(String name);

    void createTable();
}
