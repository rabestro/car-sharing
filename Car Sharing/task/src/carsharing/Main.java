package carsharing;

import carsharing.dao.impl.CompanyDaoImpl;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
//        Class.forName("org.h2.Driver");

        var dao = (args.length == 2 && "-databaseFileName".equals(args[0]))
                ? new CompanyDaoImpl(args[1])
                : new CompanyDaoImpl();

        new Application(dao).run();
    }

}