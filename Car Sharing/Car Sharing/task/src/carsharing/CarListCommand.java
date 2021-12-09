package carsharing;

import java.util.Queue;

public class CarListCommand implements Command {
  private final int companyId;

  public CarListCommand(int companyId) {
    this.companyId = companyId;
  }

  @Override
  public void execute(Queue<Command> commands) throws Exception {
    var cars = CarGateway.findAllByCompanyId(companyId);
    if (cars.isEmpty()) {
      System.out.println("The car list is empty!");
    } else {
      for (int i = 0; i < cars.size(); i++) {
        System.out.print(i + 1 + ". ");
        System.out.println(cars.get(i).getName());
      }
    }
    commands.add(new CompanyMenuCommand(companyId));
  }
}
