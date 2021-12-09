package carsharing;

import java.util.Queue;
import java.util.Scanner;

public class CustomerMenuCommand implements Command {
  private Customer customer;

  public CustomerMenuCommand(Customer customer) {
    this.customer = customer;
  }

  @Override
  public void execute(Queue<Command> commands) throws Exception {
    System.out.println("1. Rent a car\n2. Return a rented car\n3. My rented car\n0. Back");
    var s = new Scanner(System.in);
    int i = s.nextInt();
    if (i == 0) {
      commands.add(new StartMenuCommand());
    } else if (i == 1) {
      if (customer.getRentedCarId() != null && customer.getRentedCarId() != 0) {
        System.out.println("You've already rented a car!");
        commands.add(this);
      } else {
        var companies = CompanyGateway.findAll();
        if (companies.isEmpty()) {
          System.out.println("The company list is empty!");
          commands.add(new ManagerMenuCommand());
        } else {
          System.out.println("Choose the company:");
          for (int j = 0; j < companies.size(); j++) {
            System.out.print(j + 1 + ". ");
            System.out.println(companies.get(j).getName());
          }
          System.out.println("0. Back");
          int j = s.nextInt();
          if (j != 0) {
            var company = companies.get(j - 1);
            var cars = CarGateway.findAllNotRentedByCompanyId(company.getId());
            if (cars.isEmpty()) {
              System.out.println("No available cars in the " + company.getName() + " company");
            } else {
              System.out.println("Choose a car:");
              for (int k = 0; k < cars.size(); k++) {
                System.out.print(k + 1 + ". ");
                System.out.println(cars.get(k).getName());
              }
              System.out.println("0. Back");
              int k = s.nextInt();
              if (k != 0) {
                var car = cars.get(k - 1);
                customer.setRentedCarId(car.getId());
                CustomerGateway.save(customer);
                System.out.println("You rented '" + car.getName() + "'");
              }
            }
          }
          commands.add(this);
        }
      }
    } else if (i == 2) {
      if (customer.getRentedCarId() != null && customer.getRentedCarId() != 0) {
        customer.setRentedCarId(null);
        CustomerGateway.save(customer);
        System.out.println("You've returned a rented car!");
      } else {
        System.out.println("You didn't rent a car!");
      }
      commands.add(this);
    } else if (i == 3) {
      if (customer.getRentedCarId() != null && customer.getRentedCarId() != 0) {
        System.out.println(customer.getRentedCarId());
        var car = CarGateway.findById(customer.getRentedCarId());
        var company = CompanyGateway.findById(car.getCompanyId());
        System.out.println("Your rented car:\n"+car.getName()+"\nCompany:\n"+company.getName());
      } else {
        System.out.println("You didn't rent a car!");
      }
      commands.add(this);
    }
    s.close();
  }
}
