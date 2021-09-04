package carsharing;

import carsharing.dao.impl.RepositoryH2;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        var repository = (args.length == 2 && "-databaseFileName".equals(args[0]))
                ? new RepositoryH2(args[1])
                : new RepositoryH2();

        repository.createTables();

        new Application(repository).run();
    }

}