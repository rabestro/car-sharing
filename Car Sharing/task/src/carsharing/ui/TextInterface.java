package carsharing.ui;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

public interface TextInterface {
    System.Logger LOGGER = System.getLogger("");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    Scanner scanner = new Scanner(System.in);

    default void printf(final String key, final Object... args) {
        System.out.printf(getString(key), args);
    }

    default String getString(final String key) {
        return resourceBundle.containsKey(key) ? resourceBundle.getString(key) : key;
    }

    default void println() {
        System.out.println();
    }

    default void print(final String format, final Object... args) {
        System.out.print(MessageFormat.format(format, args));
    }

    default void println(final String format, final Object... args) {
        print(format, args);
        println();
    }

}