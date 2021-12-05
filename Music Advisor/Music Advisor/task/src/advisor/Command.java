package advisor;

import java.util.Queue;

public abstract class Command {
  protected Queue<Command> commands;

  public Command(Queue<Command> commands) {
    this.commands = commands;
  }

  abstract void execute() throws Exception;

  public Queue<Command> getCommands() {
    return commands;
  }
}
