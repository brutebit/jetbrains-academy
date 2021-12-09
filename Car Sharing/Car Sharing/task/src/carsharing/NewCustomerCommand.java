package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class NewCustomerCommand implements Command {
  @Override
  public void execute(Queue<Command> commands) throws Exception {
    var s = new Scanner(System.in);
    System.out.println("Enter the customer name:");
    var name = s.nextLine();
    var customer = new Customer(-1, name);
    CustomerGateway.save(customer);
    commands.add(new StartMenuCommand());
    System.out.println("The customer was created!");
  }
}
