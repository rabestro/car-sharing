package carsharing.component;

import carsharing.dao.CompanyDao;
import carsharing.ui.TextInterface;

public class CompanyMenu implements TextInterface, Runnable {
    private final CompanyDao dao;

    public CompanyMenu(CompanyDao dao) {
        this.dao = dao;
    }

    @Override
    public void run() {

    }
}
