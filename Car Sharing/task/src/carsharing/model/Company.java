package carsharing.model;

import lombok.Data;

@Data
public class Company {
    public static final Company EMPTY = new Company(0, "");

    private final int id;
    private final String name;
}
