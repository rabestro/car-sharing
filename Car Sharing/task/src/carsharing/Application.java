package carsharing;

import carsharing.ui.Menu;
import carsharing.ui.TextInterface;

public class Application implements TextInterface, Runnable {
    private final String connectionName;

    public Application(String connectionName) {
        this.connectionName = connectionName;
    }

    @Override
    public void run() {
        final var subMenu = Menu.create()
                .add("Company list", this::list)
                .add("Create a company", this::create)
                .set(Menu.Property.EXIT, "back")
                .addExit();

        final var menu = Menu.create()
                .add("Log in as a manager", subMenu)
                .addExit();

        menu.run();
    }

    private void create() {
        println("Create a company");
    }

    private void list() {
        println("List of companies");
    }
}
