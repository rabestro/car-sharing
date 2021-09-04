package carsharing.model;

import lombok.Data;

@Data
public class Car {
    private final int id;
    private final String name;
    private int companyId;
}
