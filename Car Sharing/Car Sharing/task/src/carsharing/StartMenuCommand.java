package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class StartMenuCommand implements Command {

  @Override
  public void execute(Queue<Command> commands) throws Exception {
    System.out.println("1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit");
    Scanner s = new Scanner(System.in);
    int i = s.nextInt();
    if (i == 0) {
      System.exit(0);
    } else if (i == 1) {
      commands.add(new ManagerMenuCommand());
    } else if (i == 2) {
      commands.add(new CustomerListCommand());
    } else if (i == 3) {
      commands.add(new NewCustomerCommand());
    } else {
      commands.add(this);
    }
    s.close();
  }
}
