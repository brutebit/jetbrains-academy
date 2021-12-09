package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class CustomerListCommand implements Command {
  @Override
  public void execute(Queue<Command> commands) throws Exception {
    var customers = CustomerGateway.findAll();
    if (customers.isEmpty()) {
      System.out.println("The customer list is empty!");
      commands.add(new StartMenuCommand());
    }
    else {
      System.out.println("Choose a customer:");
      for (int i = 0; i < customers.size(); i++) {
        System.out.print(i + 1 + ". ");
        System.out.println(customers.get(i).getName());
      }
      System.out.println("0. Back");
      var s = new Scanner(System.in);
      int i = s.nextInt();
      if (i == 0) {
        commands.add(new StartMenuCommand());
      } else {
        commands.add(new CustomerMenuCommand(customers.get(i - 1)));
      }
    }
  }
}
