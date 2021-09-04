package carsharing.model;

import lombok.Data;

@Data
public class Customer {
    public static final Customer EMPTY = new Customer(0, "");
    private final int id;
    private final String name;
    private int carId;
}
