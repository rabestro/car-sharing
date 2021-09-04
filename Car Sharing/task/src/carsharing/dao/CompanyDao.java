package carsharing.dao;

import carsharing.model.Company;

import java.util.Collection;
import java.util.Optional;

public interface CompanyDao {
    Collection<Company> getAllCompanies();

    void addCompany(String name);

    Optional<Company> getCompany(int id);
}
