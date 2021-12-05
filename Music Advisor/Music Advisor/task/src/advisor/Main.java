package advisor;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static advisor.Store.*;

public class Main {
  private static final Queue<Command> commands = new ConcurrentLinkedQueue<>();

  public static void main(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-access")) {
        serverPath = args[++i];
      }
      if (args[i].equals("-resource")) {
        resourceUri = args[++i];
        pagingStrategy = new ManualPagingStrategy();
      }
      if (args[i].equals("-page")) {
        page = Integer.parseInt(args[++i]);
      }
    }

    commands.add(new UserInputCommand(commands));

    try {
      while (true) {
        if (!commands.isEmpty()) {
          var command = commands.poll();
          command.execute();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
