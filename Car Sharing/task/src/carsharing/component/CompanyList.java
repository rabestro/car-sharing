package carsharing.component;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.ui.Menu;

public class CompanyList implements Component {
    private final CompanyDao dao;
    private final CarDao carDao;

    public CompanyList(CompanyDao dao, CarDao carDao) {
        this.dao = dao;
        this.carDao = carDao;
    }

    @Override
    public void run() {
        var companies = dao.getAllCompanies();
        if (companies.isEmpty()) {
            println("The company list is empty!");
            return;
        }
        var menu = Menu.create("Choose a company:");
        companies.forEach(company -> menu.add(company.getName(), () -> new CompanyMenu(company, carDao).run()));
        menu.set(Menu.Property.EXIT, "Back").onlyOnce().addExit().run();
    }

}
