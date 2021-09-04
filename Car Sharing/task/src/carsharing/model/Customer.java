package carsharing.model;

import lombok.Data;

@Data
public class Customer {
    private final int id;
    private final String name;
    private final int carId;
}
