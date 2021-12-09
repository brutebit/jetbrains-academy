package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class NewCarCommand implements Command {
  private final int companyId;

  public NewCarCommand(int companyId) {
    this.companyId = companyId;
  }

  @Override
  public void execute(Queue<Command> commands) throws Exception {
    var s = new Scanner(System.in);
    System.out.println("Enter the car name:");
    var name = s.nextLine();
    var car = new Car(-1, name, companyId);
    CarGateway.save(car);
    commands.add(new CompanyMenuCommand(companyId));
  }
}
