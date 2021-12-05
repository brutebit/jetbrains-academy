package advisor;

import java.io.IOException;

import static advisor.Store.page;

public class ApiPagingStrategy implements PagingStrategy {

  @Override
  public String handlePaging(String path, DataCommand command) throws IOException, InterruptedException {
    String response = null;
    if (command.getProps() == null)
      response = Requester.fetch(path, 0, page);
    else if (command.getPaging() == DataCommand.Paging.NEXT) {
      if (command.getProps().next == null) {
        System.out.println("No more pages");
        command.getCommands().add(new UserInputCommand(command.getCommands()));
        return null;
      }
      response = Requester.fetch(command.getProps().next);
    } else if (command.getPaging() == DataCommand.Paging.PREV) {
      if (command.getProps().prev == null) {
        System.out.println("No more pages");
        command.getCommands().add(new UserInputCommand(command.getCommands()));
        return null;
      }
      response = Requester.fetch(command.getProps().prev);
    }
    return response;
  }

  @Override
  public void printPage(DataCommand command) {
    var currentPage = (command.getProps().offset / command.getProps().limit) + 1;
    var totalPages = (command.getProps().total / command.getProps().limit) + 1;
    System.out.println("---PAGE " + currentPage + " OF " + totalPages + "---");
  }

  @Override
  public void reset() {

  }
}
