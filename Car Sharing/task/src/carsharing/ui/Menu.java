package carsharing.ui;


import java.util.*;

/**
 * An interface for a console menu system.
 */
public interface Menu extends TextInterface, Runnable {

    static Menu create() {
        return new SimpleMenu();
    }

    static Menu create(String title) {
        return new SimpleMenu().set(Property.TITLE, title);
    }

    static Menu create(ResourceBundle resourceBundle) {
        return new LocalMenu(resourceBundle);
    }

    /**
     * Set additional property for the menu.
     *
     * @param property to set
     * @param value    for the property
     * @return this menu
     */
    Menu set(Property property, String value);

    /**
     * Add new menu entry with key, description and action
     *
     * @param key         of menu entry, should be a digit, a letter or a keyword.
     * @param description of menu entry.
     * @param action      is an object implemented Runnable interface.
     * @return this menu
     */
    Menu add(String key, String description, Runnable action);

    /**
     * Add new menu entry with description and action.
     * The key will be number from 1 (menu.size + 1)
     *
     * @param description of menu entry
     * @param action      is an object implemented Runnable interface.
     * @return this menu
     */
    Menu add(String description, Runnable action);

    /**
     * Disable last added menu entry.
     *
     * @return this menu
     */
    Menu disable();

    /**
     * Disable menu entry with specified key
     *
     * @param key for the menu entry to disable.
     * @return this menu
     */
    Menu disable(String key);

    /**
     * Enable menu entry with specified key
     *
     * @param key for the menu entry to enable.
     * @return this menu
     */
    Menu enable(String key);

    /**
     * The menu should be finished after executing any menu entry
     *
     * @return this menu
     */
    Menu onlyOnce();

    /**
     * Add the exit entry to the menu.
     * <p>
     * The key is specified in Property.EXIT_KEY
     * The description is specified in Property.EXIT
     * <p>
     * If you would like to change default properties
     * you should do this with set method before addExit.
     *
     * @return this menu
     */
    Menu addExit();

    enum Property {
        TITLE("Choose your action:"),
        FORMAT("{0}. {1}"),
        ERROR("Please enter the number from 1 to {0} or 0 for exit."),
        EXIT("Exit"),
        EXIT_KEY("0");

        private final String value;

        Property(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * A default Menu implementation
     */
    class SimpleMenu implements Menu {
        protected final Map<String, MenuEntry> menu = new LinkedHashMap<>();
        protected final Map<Property, String> properties = new EnumMap<>(Property.class);
        protected MenuEntry defaultEntry = new MenuEntry("Incorrect option",
                () -> println(get(Property.ERROR), menu.size()));
        protected boolean isOnlyOnce;

        @Override
        public SimpleMenu set(Property property, String value) {
            properties.put(property, value);
            return this;
        }

        @Override
        public SimpleMenu add(String key, String description, Runnable action) {
            menu.put(key, new MenuEntry(description, action));
            return this;
        }

        @Override
        public SimpleMenu add(String description, Runnable action) {
            return this.add(String.valueOf(menu.size() + 1), description, action);
        }

        @Override
        public SimpleMenu disable() {
            disable(String.valueOf(menu.size()));
            return this;
        }

        @Override
        public SimpleMenu disable(String key) {
            menu.get(key).isEnabled = false;
            return this;
        }

        @Override
        public SimpleMenu enable(String key) {
            menu.get(key).isEnabled = true;
            return this;
        }

        @Override
        public SimpleMenu onlyOnce() {
            isOnlyOnce = true;
            return this;
        }

        @Override
        public SimpleMenu addExit() {
            menu.put(get(Property.EXIT_KEY), new MenuEntry(get(Property.EXIT), this::onlyOnce));
            return this;
        }

        @Override
        public void run() {
            do {
                println();
                println(get(Property.TITLE));
                menu.forEach((key, entry) -> {
                    if (entry.isEnabled) {
                        println(get(Property.FORMAT), key, entry);
                    }
                });
                final var key = scanner.nextLine().toLowerCase();
                println();
                menu.getOrDefault(key, defaultEntry).run();
            } while (!isOnlyOnce);
        }

        String get(Property property) {
            return properties.getOrDefault(property, property.getValue());
        }

        static final class MenuEntry implements Runnable {
            private final String description;
            private final Runnable action;
            boolean isEnabled = true;

            MenuEntry(final String description, final Runnable action) {
                this.description = description;
                this.action = action;
            }

            @Override
            public String toString() {
                return description;
            }

            @Override
            public void run() {
                action.run();
            }
        }
    }

    /**
     * Internalized Menu implementation
     */
    class LocalMenu extends SimpleMenu {
        protected final ResourceBundle bundle;

        public LocalMenu(ResourceBundle bundle) {
            super();
            this.bundle = bundle;

            for (var property : Property.values()) {
                var key = "menu." + property.name().toLowerCase();
                if (bundle.containsKey(key)) {
                    super.set(property, bundle.getString(key));
                }
            }
        }

        @Override
        public SimpleMenu add(String key, String description, Runnable action) {
            return super.add(key, bundle.getString(description), action);
        }

        @Override
        public SimpleMenu add(String description, Runnable action) {
            return super.add(String.valueOf(menu.size() + 1), bundle.getString(description), action);
        }

    }
}