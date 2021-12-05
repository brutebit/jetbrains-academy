package advisor;

import java.util.Queue;

public class ExitCommand extends Command{

  public ExitCommand(Queue<Command> commands) {
    super(commands);
  }

  @Override
  void execute() {
    System.exit(0);
  }
}
