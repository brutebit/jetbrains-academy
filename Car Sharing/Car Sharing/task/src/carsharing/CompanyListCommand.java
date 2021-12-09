package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class CompanyListCommand implements Command {

  @Override
  public void execute(Queue<Command> commands) throws Exception {
    var companies = CompanyGateway.findAll();
    if (companies.isEmpty()) {
      System.out.println("The company list is empty!");
      commands.add(new ManagerMenuCommand());
    } else {
      System.out.println("Choose the company:");
      for (int i = 0; i < companies.size(); i++) {
        System.out.print(i + 1 + ". ");
        System.out.println(companies.get(i).getName());
      }
      System.out.println("0. Back");
      var s = new Scanner(System.in);
      int i = s.nextInt();
      if (i == 0) {
        commands.add(new ManagerMenuCommand());
      } else {
        commands.add(new CompanyMenuCommand(companies.get(i - 1).getId()));
      }
    }
  }
}
