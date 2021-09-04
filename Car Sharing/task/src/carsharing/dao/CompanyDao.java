package carsharing.dao;

import carsharing.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyDao {
    List<Company> getAllCompanies();

    void addCompany(String name);

    Optional<Company> getCompany(int id);
}
