package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class ManagerMenuCommand implements Command {

  @Override
  public void execute(Queue<Command> commands) throws Exception {
    System.out.println("1. Company list\n2. Create a company\n0. Back");
    Scanner s = new Scanner(System.in);
    int i = s.nextInt();
    if (i == 0) {
      commands.add(new StartMenuCommand());
    } else if (i == 1) {
      commands.add(new CompanyListCommand());
    } else if (i == 2) {
      commands.add(new NewCompanyCommand());
    } else {
      commands.add(this);
    }
    s.close();
  }
}
