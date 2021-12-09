package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class NewCompanyCommand implements Command {

  @Override
  public void execute(Queue<Command> commands) throws Exception {
    System.out.println("Enter the company name: ");
    Scanner s = new Scanner(System.in);
    var name = s.nextLine();
    CompanyGateway.save(new Company(-1, name));
    System.out.println("The company was created!");
    commands.add(new ManagerMenuCommand());
  }
}
