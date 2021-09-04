package carsharing.component;

import carsharing.dao.CustomerDao;
import carsharing.ui.TextInterface;
import lombok.AllArgsConstructor;

import java.util.stream.IntStream;

@AllArgsConstructor
public class CustomerMenu implements TextInterface, Runnable {
    private final CustomerDao customerDao;

    @Override
    public void run() {
        var customers = customerDao.getAllCustomers();
        if (customers.isEmpty()) {
            println("The customer list is empty!");
            return;
        }
        var iterator = customers.iterator();
        IntStream.range(0, customers.size())
                .forEach(i -> println("{0}. {1}", i + 1, iterator.next().getName()));
    }
}
