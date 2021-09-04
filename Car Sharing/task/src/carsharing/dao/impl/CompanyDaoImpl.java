package carsharing.dao.impl;

import carsharing.dao.CompanyDao;
import carsharing.dao.Repository;
import carsharing.model.Company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.lang.System.Logger.Level.TRACE;

public class CompanyDaoImpl implements CompanyDao {
    System.Logger LOGGER = System.getLogger("");

    private static final String SQL_COMPANIES = "SELECT * FROM company";

    private final Repository repository;

    public CompanyDaoImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Company> getAllCompanies() {
        try (var connection = repository.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(SQL_COMPANIES)) {
            var companies = new ArrayList<Company>();
            while (resultSet.next()) {
                var company = new Company();
                company.setId(resultSet.getInt("ID"));
                company.setName(resultSet.getString("NAME"));
                companies.add(company);
            }
            return Collections.unmodifiableCollection(companies);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptySet();
    }


    @Override
    public void addCompany(String name) {
        try (var connection = repository.getConnection();
             var statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            final var sql = "INSERT INTO COMPANY (name) VALUES ('" + name + "')";
            LOGGER.log(TRACE, sql);
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Company> getCompany(int id) {
        LOGGER.log(TRACE, "searching company id={0}", id);
        try (var connection = repository.getConnection();
             var statement = connection.createStatement()) {
            final var sql = "SELECT id, name FROM COMPANY WHERE id=" + id;

            var resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                var company = new Company();
                company.setId(resultSet.getInt("ID"));
                company.setName(resultSet.getString("NAME"));
                return Optional.of(company);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
