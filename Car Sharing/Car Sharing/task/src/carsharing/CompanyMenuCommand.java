package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class CompanyMenuCommand implements Command {
  private final int companyId;

  public CompanyMenuCommand(int companyId) {
    this.companyId = companyId;
  }

  @Override
  public void execute(Queue<Command> commands) throws Exception {
    var company = CompanyGateway.findById(companyId);
    System.out.println(company.getName() + " company:\n1. Car list\n2. Create a car\n0. Back");
    var s = new Scanner(System.in);
    int i = s.nextInt();
    if (i == 0) {
      commands.add(new ManagerMenuCommand());
    } else if (i == 1) {
      commands.add(new CarListCommand(companyId));
    } else if (i == 2) {
      commands.add(new NewCarCommand(companyId));
    } else {
      commands.add(this);
    }
    s.close();
  }
}
