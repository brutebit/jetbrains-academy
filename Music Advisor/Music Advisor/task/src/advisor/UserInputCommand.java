package advisor;

import java.util.Queue;
import java.util.Scanner;

public class UserInputCommand extends Command {
  private static final Scanner scanner = new Scanner(System.in);
  private static DataCommand lastCommand = null;

  public UserInputCommand(Queue<Command> commands) {
    super(commands);
  }

  @Override
  void execute() {
    var input = scanner.next();

    switch (input) {
      case "auth":
        commands.add(new AuthCommand(commands));
        break;
      case "new":
        if (checkLogin()) {
          lastCommand = new NewReleasesDataCommand(commands);
          Store.pagingStrategy.reset();
          commands.add(lastCommand);
        }
        break;
      case "featured":
        if (checkLogin()) {
          lastCommand = new FeaturedPlaylistsDataCommand(commands);
          Store.pagingStrategy.reset();
          commands.add(lastCommand);
        }
        break;
      case "categories":
        if (checkLogin()) {
          lastCommand = new CategoriesDataCommand(commands);
          Store.pagingStrategy.reset();
          commands.add(lastCommand);
        }
        break;
      case "playlists":
        if (checkLogin()) {
          var name = scanner.nextLine();
          lastCommand = new CategoryPlaylistsDataCommand(commands, name.trim());
          Store.pagingStrategy.reset();
          commands.add(lastCommand);
        }
        break;
      case "next":
        lastCommand.setPaging(DataCommand.Paging.NEXT);
        commands.add(lastCommand);
        break;
      case "prev":
        lastCommand.setPaging(DataCommand.Paging.PREV);
        commands.add(lastCommand);
        break;

      case "exit":
        System.out.println("---GOODBYE!---");
        Store.pagingStrategy.reset();
        commands.add(new ExitCommand(commands));
        break;
      default:
        System.out.println("Unknown command.");
        Store.pagingStrategy.reset();
        commands.add(new UserInputCommand(commands));
    }
  }

  private boolean checkLogin() {
    if (!Store.isLoggedIn) {
      System.out.println("Please, provide access for application.");
      commands.add(new UserInputCommand(commands));
      return false;
    }
    return true;
  }
}
