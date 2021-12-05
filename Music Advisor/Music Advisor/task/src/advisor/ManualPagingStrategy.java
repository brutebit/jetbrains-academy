package advisor;

import static advisor.Store.clientId;
import static advisor.Store.page;


public class ManualPagingStrategy implements PagingStrategy {
  private int currentOffset = 0;
  private int totalPages = 0;

  @Override
  public String handlePaging(String path, DataCommand command) throws Exception {
    String response = null;
    if (command.getProps() == null)
      response = Requester.fetch(path, currentOffset, page);
    else if (command.getPaging() == DataCommand.Paging.NEXT) {
      currentOffset += page;
      if (currentOffset >= totalPages) {
        System.out.println("No more pages");
        command.getCommands().add(new UserInputCommand(command.getCommands()));
        currentOffset -= page;
        return null;
      }
      response = Requester.fetch(path, currentOffset, page);
    } else if (command.getPaging() == DataCommand.Paging.PREV) {
      currentOffset -= page;
      if (currentOffset < 0) {
        System.out.println("No more pages");
        command.getCommands().add(new UserInputCommand(command.getCommands()));
        currentOffset += page;
        return null;
      }
      command.setCurrentOffset(currentOffset);
      response = Requester.fetch(path, currentOffset, page);
    }
    command.setCurrentOffset(currentOffset);
    return response;
  }

  @Override
  public void printPage(DataCommand command) {
    var total = command.getProps().total;
    var remainder = total % page;
    totalPages = remainder == 0 ? total / page : (total / page) + 1;
    var offsetTotalRatio = (currentOffset + 1) / totalPages;
    remainder = (currentOffset + 1) % totalPages;
    var currentPage = offsetTotalRatio == 1 ? totalPages : remainder == 0 ? remainder+1 : remainder;
    System.out.println("---PAGE " + currentPage + " OF " + totalPages + "---");
  }

  @Override
  public void reset() {
    currentOffset = 0;
  }
}
