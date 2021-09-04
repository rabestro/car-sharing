package carsharing.dao.impl;

import carsharing.dao.CompanyDao;
import carsharing.dao.Repository;
import carsharing.model.Company;
import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.System.Logger.Level.ERROR;

@AllArgsConstructor
public class CompanyDaoImpl implements CompanyDao {
    private static final System.Logger LOGGER = System.getLogger("");

    private static final String SQL_COMPANIES = "SELECT * FROM company";
    private static final String SQL_COMPANY = "SELECT id, name FROM COMPANY WHERE id=?";
    private static final String SQL_INSERT_COMPANY = "INSERT INTO COMPANY (name) VALUES (?)";

    private static final Function<ResultSet, Company> COMPANY_BUILDER = rs -> {
        try {
            return new Company(rs.getInt(1), rs.getString(2));
        } catch (SQLException e) {
            LOGGER.log(ERROR, e::getMessage);
        }
        return Company.EMPTY;
    };

    private final Repository repository;

    @Override
    public List<Company> getAllCompanies() {
        return repository.select(SQL_COMPANIES, COMPANY_BUILDER);
    }

    @Override
    public void addCompany(String name) {
        repository.update(SQL_INSERT_COMPANY, name);
    }

    @Override
    public Optional<Company> getCompany(int id) {
        return repository.select(SQL_COMPANY, COMPANY_BUILDER, id).stream().findAny();
    }
}
