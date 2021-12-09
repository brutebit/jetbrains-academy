package carsharing;


import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
  private static final Queue<Command> commands = new ArrayDeque<>();

  public static void main(String[] args) throws Exception {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-databaseFileName")) {
        Config.databaseName = args[++i];
      }
    }

    Database.create();
    commands.add(new StartMenuCommand());
    while (!commands.isEmpty()) {
      commands.poll().execute(commands);
    }
  }
}
