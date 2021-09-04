package carsharing.dao.impl;

import carsharing.dao.Repository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerDaoImpl {
    private static final System.Logger LOGGER = System.getLogger("");

    private static final String SQL_COMPANIES = "SELECT * FROM company";
    private static final String SQL_INSERT_COMPANY = "INSERT INTO COMPANY (name) VALUES (?)";

    private final Repository repository;


}
