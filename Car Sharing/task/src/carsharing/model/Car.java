package carsharing.model;

import lombok.Data;

@Data
public class Car {
    public static final Car EMPTY = new Car(0, "", 0);

    private final int id;
    private final String name;
    private final int companyId;
}
